using System;
using WeatherApi.Models;
using Newtonsoft.Json;
using static System.Net.WebRequestMethods;

namespace WeatherApi
{
    class Program
    {
        private const string api_key = ""; //type your api key here

        static async Task Main(string[] args)
        {
            Console.WriteLine("Select an option: \n" +
                "1. Check the weather in selected city\n" +
                "2. Check the weather in 50 top cities");
            int option = int.Parse(Console.ReadLine());
            if(option == 1) {
                Console.WriteLine("Type the city you want to check the weather: ");
                string query = Console.ReadLine();
                string apiUrl = $"http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey={api_key}&q={query}";
                City[] cities;
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    cities = JsonConvert.DeserializeObject<City[]>(json);
                }
                Console.WriteLine("Choose the City:");
                for (int i = 1; i <= cities.Length; i++)
                {
                    Console.WriteLine(i + ". " + cities[i - 1].LocalizedName + "-" + cities[i - 1].AdministrativeArea.LocalizedName);
                }
                string cityId = Console.ReadLine();
                string locationId = cities[int.Parse(cityId) - 1].Key;
                apiUrl = $"http://dataservice.accuweather.com/currentconditions/v1/{locationId}?apikey={api_key}";
                Weather[] weathers;
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    weathers = JsonConvert.DeserializeObject<Weather[]>(json);
                }
                Console.WriteLine("In " + cities[int.Parse(cityId) - 1].LocalizedName + " there is " + weathers.FirstOrDefault().Temperature.Metric.Value + " Celsius degrees");
                apiUrl = $"http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/{locationId}?apikey={api_key}&metric=true";
                ForecastHour[] forecasts;
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    forecasts = JsonConvert.DeserializeObject<ForecastHour[]>(json);
                }
                Console.WriteLine("In one hour there will be " + forecasts.FirstOrDefault().Temperature.Value + " Celsius degrees");
                apiUrl = $"http://dataservice.accuweather.com/indices/v1/daily/1day/{locationId}/-10/?apikey={api_key}&metric=true&details=true";
                AirQuality[] airQuality;
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    airQuality = JsonConvert.DeserializeObject<AirQuality[]>(json);
                }
                Console.WriteLine(airQuality.FirstOrDefault().Text);
                
                apiUrl = $"http://dataservice.accuweather.com//forecasts/v1/daily/1day/{locationId}?apikey={api_key}&metric=true";
                ForecastDay forecastDays;
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    forecastDays = JsonConvert.DeserializeObject<ForecastDay>(json);
                }
                Console.WriteLine("Today the minimum temperature is " + forecastDays.DailyForecasts.FirstOrDefault().Temperature.Minimum.Value + " and the maximum is " + forecastDays.DailyForecasts.FirstOrDefault().Temperature.Maximum.Value);
                
                apiUrl = $"http://dataservice.accuweather.com/currentconditions/v1/{locationId}/historical?apikey={api_key}";
                Weather[] weathers2; 
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    weathers2 = JsonConvert.DeserializeObject<Weather[]>(json);
                }
                for(int i = 1; i <= weathers2.Length; i++)
                {
                    Console.WriteLine("The temperature " + i + " hour/s ago was " + weathers2[i - 1].Temperature.Metric.Value);
                }

            }
            else if(option == 2)
            {
                string apiUrl = $"http://dataservice.accuweather.com/currentconditions/v1/topcities/50?apikey={api_key}";
                List<TopCities> topcities;
                using (HttpClient client = new HttpClient())
                {
                    var response = await client.GetAsync(apiUrl);
                    string json = await response.Content.ReadAsStringAsync();
                    topcities = JsonConvert.DeserializeObject<List<TopCities>>(json);
                }
                for (int i = 1; i <= 50;i++)
                {
                    Console.WriteLine(i + ". " + topcities[i-1].LocalizedName +" "+ topcities[i-1].Temperature.Metric.Value + " Celsius degrees");
                }
            }


        }

        
    }
}