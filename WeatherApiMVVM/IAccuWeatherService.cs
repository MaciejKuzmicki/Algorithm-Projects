using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WeatherApi.Models;

namespace WeatherApiMVVM
{
    public interface IAccuWeatherService
    {
        Task<City[]> GetLocations(string locationName);
        Task<Weather> GetCurrentConditions(string cityKey);
    }
}
