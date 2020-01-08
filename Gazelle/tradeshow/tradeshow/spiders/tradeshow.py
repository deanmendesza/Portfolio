import scrapy 
from scrapy_splash import SplashRequest

class TradeShow(scrapy.Spider):

    name = 'trade'

    def start_requests(self):

        yield SplashRequest(
            url = 'https://himss20.mapyourshow.com/8_0/explore/exhibitor-alphalist.cfm#/',
            callback=self.parse,
            args={'wait':2}
        )
    
    def parse(self, response):
        
        links_to_follow = response.xpath('//*[@id="exhibitor-result-card"]//li/div/div/div[2]/h3/a/@href').extract()
        # follow each link 
        for link in links_to_follow:

            yield response.follow(
                url=link, 
                callback=self.parse_pages
                )

    def parse_pages(self, response):

        # extract company names and details, then strip unnecessary characters
        company_name = response.css('div#js-Vue-MyShow>h1::text').extract().strip()
        company_details = response.css('div.w-100>div.lh-copy::text').extract().strip()
        # zip together names and details
        joint_info = zip(company_name, company_details)

        for j in joint_info:
            scraped_info = {
                'Company': j[0],
                'Details': j[1]
            }
            yield scraped_info