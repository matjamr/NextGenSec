import requests
import logging


class APIClient:
    def __init__(self, base_url, headers=None):
        self.base_url = base_url
        self.headers = headers or {}
        logging.basicConfig(level=logging.INFO)
        self.logger = logging.getLogger(__name__)

    def _build_url(self, endpoint):
        return f"{self.base_url}/{endpoint}"

    def _handle_response(self, response):
        if response.status_code == 200:
            return response.json()
        else:
            self.logger.error(f"API request failed with status {response.status_code}: {response.text}")
            response.raise_for_status()

    def get(self, endpoint, params=None):
        url = self._build_url(endpoint)
        response = requests.get(url, headers=self.headers, params=params)
        return self._handle_response(response)

    def post(self, endpoint, data=None, json=None):
        url = self._build_url(endpoint)
        response = requests.post(url, headers=self.headers, data=data, json=json)
        return self._handle_response(response)

    def put(self, endpoint, data=None, json=None):
        url = self._build_url(endpoint)
        response = requests.put(url, headers=self.headers, data=data, json=json)
        return self._handle_response(response)

    def delete(self, endpoint):
        url = self._build_url(endpoint)
        response = requests.delete(url, headers=self.headers)
        return self._handle_response(response)
