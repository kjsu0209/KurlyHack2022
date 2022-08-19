from datetime import datetime

from selenium import webdriver
from selenium.common.exceptions import TimeoutException, NoSuchElementException, ElementClickInterceptedException, \
    ElementNotInteractableException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait  # available since 2.4.0
from selenium.webdriver.support import expected_conditions as EC  # available since 2.26.0
import time
from selenium.webdriver.firefox.options import Options

from urllib.parse import urlparse
from urllib.parse import parse_qs
import json
import os
options = Options()
options.headless = True

CTG_URL = 'https://www.amoremall.com/kr/ko/display/rank?category={CTG_ID}&categoryType=CATEGORY'
CTG_LIST = ['CTG007', 'CTG008']
IMG_URL = 'https://images-kr.amoremall.com/products/{id}/{id}_01.png?1659942889742&1659942889743&resize=550:550&shrink=550:550'


def open_webdriver(url):
    driver = webdriver.Firefox(options=options, executable_path='/opt/homebrew/bin/geckodriver')
    driver.get(url)
    return driver


def click_review(driver):
    WebDriverWait(driver, 100).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, 'button[ap-click-name="상품상세_리뷰탭"]'))).click()


def find_element(driver, selector):
    WebDriverWait(driver, 10000).until(
        EC.element_to_be_clickable((By.CSS_SELECTOR, selector)))
    return driver.find_element(By.CSS_SELECTOR, selector)


def click_review_more(driver):
    load_count = 100
    no_more_btn = False
    for l in range(0, load_count):
        timeout = datetime.now().minute
        while True:
            try:
                driver.find_element(By.XPATH, "//*[contains(text(), '더 보기')]").click()
                break
            except ElementClickInterceptedException:
                if abs(timeout - datetime.now().minute) >= 2:
                    print(f'timeout: {l} page will be parsed.')
                    return
                driver.execute_script("arguments[0].scrollIntoView()", driver.find_element(By.XPATH, "//*[contains(text(), '더 보기')]"))
            except ElementNotInteractableException or NoSuchElementException:
                no_more_btn = True
                break
        if no_more_btn:
            break


def scroll(driver, start, end):
    for i in range(start, end):
        driver.execute_script(f"window.scrollTo(0, {i})")
        time.sleep(0.0001)


def get_age_group(age_group):
    return int(age_group.split('0대')[0])


def get_rate(rate):
    return int(rate.split('star')[1])


def get_user_attrs(elements):
    attr = {}
    attr_name = ['age_group', 'gender', 'skin_type', 'skin_info']
    attr_idx = 0
    for e in elements:
        name = attr_name[attr_idx] if attr_idx < len(attr_name) else 'a' + str(attr_idx)
        if name == 'age_group' and e.text.split('0대')[0].isnumeric():
            attr[name] = get_age_group(e.text)
        elif name == 'age_group' and not e.text.split('0대')[0].isnumeric():
            if e.text in ['남성', '여성']:
                attr_idx += 1
            else:
                attr_idx += 2
            name = attr_name[attr_idx] if attr_idx < len(attr_name) else 'a' + str(attr_idx)
            attr[name] = e.text
        else:
            attr[name] = e.text
        attr_idx += 1

    return attr


def get_data(driver, item_id, ctg_id):
    click_review(driver)
    click_review_more(driver)

    item = {
        'item_id': item_id,
        'item_img': IMG_URL.format(id=item_id),
        'brand_name': driver.find_element(By.CSS_SELECTOR, 'div.brandArea > a.brand').text,
        'category_id': ctg_id,
        'item_name': driver.find_element(By.CSS_SELECTOR, 'div.name > strong').text,
        'price': driver.find_element(By.CSS_SELECTOR, 'div.priceArea > span.price > strong.val').text,
        'avg_rating': driver.find_element(By.CSS_SELECTOR, 'div.reviewSummary > div.score > div.num').text
        }

    elements = driver.find_elements(By.CSS_SELECTOR, 'div.reviewCard')
    reviews = []
    for e in elements:
        try:
            driver.execute_script("arguments[0].scrollIntoView()", e)
            user_attrs = get_user_attrs(e.find_elements(By.CSS_SELECTOR, 'div.info > div.item > span.txt'))
            review = {
                'user_id': e.find_element(By.CSS_SELECTOR, 'div.userArea > span.name').text,
                'user_rating': get_rate(e.find_element(By.CSS_SELECTOR, 'div.icoStarWrap').get_attribute('class')),
                'user_attr': user_attrs,
                'review': e.find_element(By.CSS_SELECTOR, 'p.txt').text
            }
            reviews.append(review)
        except Exception as e:
            print(f'skip review: {e}')

    item['review'] = reviews

    return item


def parse_url(url, param):
    parsed_url = urlparse(url)
    captured_value = parse_qs(parsed_url.query)[param][0]
    return captured_value


def write_data(file, data):
    if os.path.exists(file):
        with open(file, 'r') as fp:
            origin = json.load(open(file))
            origin['data'].append(data)

        with open(file, 'w') as outfile:
            json.dump(origin, outfile, ensure_ascii=False)
    else:
        with open(file, 'a+') as fp:
            origin = {'data': [data]}
            json.dump(origin, fp, ensure_ascii=False)


def main():
    result_file = 'result_{ctg}_{idx}.json'

    for ctg in CTG_LIST:
        driver = open_webdriver(CTG_URL.format(CTG_ID=ctg))
        result_idx = 1

        scroll(driver, 1, 2000)

        # 이벤트 팝업 close
        height = 2000
        timeout = datetime.now().minute
        while True:
            try:
                driver.find_element(By.CSS_SELECTOR, '.qg-inweb-close').click()
                break
            except ElementClickInterceptedException:
                driver.execute_script(f"window.scrollTo({height}, {height + 1})")
                if abs(timeout - datetime.now().minute) >= 2:
                    print(f'timeout: event popup closing skipped')
                    break
                height += 1
            except NoSuchElementException:
                driver.execute_script(f"window.scrollTo({height}, {height + 1})")
                height += 1
                time.sleep(1)
                if abs(timeout - datetime.now().minute) >= 2:
                    print(f'timeout: event popup closing skipped')
                    break
                pass

        # 프로덕트 카드 가져오기
        elements = driver.find_elements(By.CSS_SELECTOR, 'div.productCard > a')
        items = []
        for e in elements:
            item = {'url': e.get_attribute('href')}
            item['id'] = parse_url(item['url'], 'ITEM_VALUE')
            items.append(item)

        start_stamp = datetime.now().minute
        for idx, i in enumerate(items):
            if abs(datetime.now().minute - start_stamp) >= 5:
                print(f"[{datetime.now().strftime('%H:%M:%S')}] {idx + 1}/{len(items)}")
                start_stamp = datetime.now().minute

            driver.get(i['url'])
            try:
                data = get_data(driver, i['id'], ctg)
                if os.path.exists("./" + result_file.format(ctg=ctg, idx=result_idx)) and os.path.getsize("./" + result_file.format(ctg=ctg, idx=result_idx)) >= 1000000:
                    result_idx += 1
                write_data(result_file.format(ctg=ctg, idx=result_idx), data)
            except Exception as e:
                print(f'skip: {e}')

        d = datetime.now().strftime("%H:%M:%S")
        print(f'[{d}] {ctg} done. Total {len(items)} items dumped.')

        driver.quit()


if __name__ == '__main__':
    main()