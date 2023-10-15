using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApi.Models
{
    internal class AirQuality
    {
        public string Name { get; set; }
        public int ID { get; set; }
        public bool Ascending { get; set; }
        public DateTime LocalDateTime { get; set; }
        public int EpochDateTime { get; set; }
        public double Value { get; set; }
        public string Category { get; set; }
        public string Text { get; set; }
        public int CategoryValue { get; set; }
        public string MobileLink { get; set; }
        public string Link { get; set; }
    }
}

