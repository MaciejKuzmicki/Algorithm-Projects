using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WeatherApi.Models;

namespace WeatherApiMVVM.ViewModels
{
    public class WeatherViewModel
    {
        public WeatherViewModel(Weather weather)
        {
            CurrentTemperature = weather.Temperature.Metric.Value;
        }
        public double CurrentTemperature { get; set; }
    }

}
