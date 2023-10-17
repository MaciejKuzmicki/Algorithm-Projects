using System;
using WeatherApi.Models;
using Newtonsoft.Json;
using static System.Net.WebRequestMethods;
using System.Net.Http;
using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using WeatherApiMVVM;

namespace WeatherApi
{
    public class AccuWeatherService : IAccuWeatherService
    {
        private const string api_key = ""; //type your api key here


        public async Task<City[]> GetLocations(string query)
        {
            string apiUrl = $"http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey={api_key}&q={query}";
            City[] cities;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                cities = JsonConvert.DeserializeObject<City[]>(json);
            }
            return cities;
        }

        public async Task<Weather> GetCurrentConditions(string cityId)
        {
            string apiUrl = $"http://dataservice.accuweather.com/currentconditions/v1/{cityId}?apikey={api_key}";
            Weather[] weathers;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                weathers = JsonConvert.DeserializeObject<Weather[]>(json);
            }
            return weathers.FirstOrDefault();
        }

        public async Task<ForecastHour> GetForecast(string cityId)
        {
            string apiUrl = $"http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/{cityId}?apikey={api_key}&metric=true";
            ForecastHour[] forecasts;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                forecasts = JsonConvert.DeserializeObject<ForecastHour[]>(json);
            }
            return forecasts.FirstOrDefault();
        }

        public async Task<AirQuality> GetAirQuality(string cityId)
        {
            string apiUrl = $"http://dataservice.accuweather.com/indices/v1/daily/1day/{cityId}/-10/?apikey={api_key}&metric=true&details=true";
            AirQuality[] airQuality;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                airQuality = JsonConvert.DeserializeObject<AirQuality[]>(json);
            }
            return airQuality.FirstOrDefault();
        }

        public async Task<ForecastDay> GetForecastDay(string cityId)
        {
            string apiUrl = $"http://dataservice.accuweather.com//forecasts/v1/daily/1day/{cityId}?apikey={api_key}&metric=true";
            ForecastDay forecastDays;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                forecastDays = JsonConvert.DeserializeObject<ForecastDay>(json);
            }
            return forecastDays;
        }

        public async Task<Weather> GetWeatherHistorical(string cityId)
        {
            string apiUrl = $"http://dataservice.accuweather.com/currentconditions/v1/{cityId}/historical?apikey={api_key}";
            Weather[] weathers2;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                weathers2 = JsonConvert.DeserializeObject<Weather[]>(json);
            }
            return weathers2.FirstOrDefault();
        }

        public async Task<List<TopCities>> GetTopCities()
        {
            string apiUrl = $"http://dataservice.accuweather.com/currentconditions/v1/topcities/50?apikey={api_key}";
            List<TopCities> topcities;
            using (HttpClient client = new HttpClient())
            {
                var response = await client.GetAsync(apiUrl);
                string json = await response.Content.ReadAsStringAsync();
                topcities = JsonConvert.DeserializeObject<List<TopCities>>(json);
            }
            return topcities;
        }
    }
}