using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApi.Models
{
    public class TopCities
    {
        public string Key { get; set; }
        public string LocalizedName { get; set; }
        public string EnglishName { get; set; }
        public Countrys Country { get; set; }
        public TimeZone TimeZone { get; set; }
        public GeoPosition GeoPosition { get; set; }
        public DateTime LocalObservationDateTime { get; set; }
        public int EpochTime { get; set; }
        public string WeatherText { get; set; }
        public int WeatherIcon { get; set; }
        public bool HasPrecipitation { get; set; }
        public string PrecipitationType { get; set; }
        public bool IsDayTime { get; set; }
        public Temperature Temperature { get; set; }
        public string MobileLink { get; set; }
        public string Link { get; set; }
    }
}
