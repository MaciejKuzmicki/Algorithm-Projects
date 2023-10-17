using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WeatherApi.Models;

namespace WeatherApiMVVM.ViewModels
{
    public class TopCitiesViewModel
    {
        public double Temperature {  get; set; }
        public string Name { get; set; }
        public TopCitiesViewModel(TopCities topCities) 
        {
            this.Temperature = topCities.Temperature.Metric.Value;
            this.Name = topCities.LocalizedName;
        }
    }
}
