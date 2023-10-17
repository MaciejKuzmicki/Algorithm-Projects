using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApi.Models
{
    public class Minimum
    {
        public double Value { get; set; }
        public string Unit { get; set; }
        public int UnitType { get; set; }
    }
}
