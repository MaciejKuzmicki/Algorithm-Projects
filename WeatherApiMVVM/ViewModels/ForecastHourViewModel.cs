using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WeatherApi.Models;

namespace WeatherApiMVVM.ViewModels
{
    public class ForecastHourViewModel
    {
        public double Temperature { get; set; }
        public ForecastHourViewModel(ForecastHour forecastHour) 
        {
            this.Temperature = forecastHour.Temperature.Value;
        }
    }
}
