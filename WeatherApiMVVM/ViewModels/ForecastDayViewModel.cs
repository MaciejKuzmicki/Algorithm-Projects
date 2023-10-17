using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WeatherApi.Models;

namespace WeatherApiMVVM.ViewModels
{
    public class ForecastDayViewModel
    {
        public double min {  get; set; }
        public double max { get; set; }
        public ForecastDayViewModel(ForecastDay forecastDay) {
            this.min = forecastDay.DailyForecasts.FirstOrDefault().Temperature.Minimum.Value;
            this.max = forecastDay.DailyForecasts.FirstOrDefault().Temperature.Maximum.Value;
        }
    }
}
