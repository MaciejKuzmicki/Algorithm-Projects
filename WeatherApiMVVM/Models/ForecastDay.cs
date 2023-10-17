using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApi.Models
{
    public class ForecastDay
    {
        public Headline Headline { get; set; }
        public List<DailyForecast> DailyForecasts { get; set; }
    }
}
