using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections.ObjectModel;
using CommunityToolkit.Mvvm.ComponentModel;
using WeatherApi;
using CommunityToolkit.Mvvm.Input;
using WeatherApi.Models;
using Newtonsoft.Json.Bson;
using System.Xml.Serialization;

namespace WeatherApiMVVM.ViewModels
{
    public partial class MainViewModel : ObservableObject
    {

        AccuWeatherService _weatherService;
        private CityViewModel _selectedCity;

        [ObservableProperty]
        private WeatherViewModel weatherView;
        [ObservableProperty]
        private ForecastHourViewModel forecastHourView;
        [ObservableProperty]
        private AirQualityViewModel airQualityView;
        [ObservableProperty]
        private ForecastDayViewModel forecastDayView;
        [ObservableProperty]
        private WeatherViewModel weatherView1;

        

        public CityViewModel SelectedCity
        {
            get => _selectedCity;
            set
            {
                _selectedCity = value;
                OnPropertyChanged();
                LoadWeather();
                LoadOneHourForecast();
                LoadAirQuality();
                LoadMinAndMaxTemperature();
                LoadWeathers();
            }
        }

        private Weather _weather;

        private Weather _weathers1;
       
        private ForecastHour _forecastHour;

        private AirQuality _airQuality;

        private ForecastDay _forecastDay;

        public ObservableCollection<CityViewModel> Cities { get; }

        public ObservableCollection<TopCitiesViewModel> TopCities { get; }

        public MainViewModel(AccuWeatherService accuWeatherService) 
        { 
            _weatherService = accuWeatherService;
            Cities = new ObservableCollection<CityViewModel>();
            TopCities = new ObservableCollection<TopCitiesViewModel>();
        }

        [RelayCommand]
        public async Task LoadCities(string locationName)
        {
            // podejście nr 2:
            var cities = await _weatherService.GetLocations(locationName);
            TopCities.Clear();
            Cities.Clear();
            foreach (var city in cities)
                Cities.Add(new CityViewModel(city));
        }

        [RelayCommand]
        public async Task LoadTopCities()
        {
            var topcities = await _weatherService.GetTopCities();
            Cities.Clear();
            TopCities.Clear();
            foreach (var city in topcities)
                TopCities.Add(new TopCitiesViewModel(city));
        }

        public async void LoadWeathers()
        {
            _weathers1 = await _weatherService.GetWeatherHistorical(SelectedCity.Key);
            WeatherView1 = new WeatherViewModel(_weathers1);
        }

        
        private async void LoadWeather()
        {
         
            _weather = await _weatherService.GetCurrentConditions(SelectedCity.Key);
            WeatherView = new WeatherViewModel(_weather);
        }

        private async void LoadOneHourForecast()
        {
            _forecastHour = await _weatherService.GetForecast(SelectedCity.Key);
            ForecastHourView = new ForecastHourViewModel(_forecastHour);
        }

        private async void LoadAirQuality()
        {
            _airQuality = await _weatherService.GetAirQuality(SelectedCity.Key);
            AirQualityView = new AirQualityViewModel(_airQuality);
        }

        private async void LoadMinAndMaxTemperature()
        {
            _forecastDay = await _weatherService.GetForecastDay(SelectedCity.Key);
            ForecastDayView =  new ForecastDayViewModel(_forecastDay);
        }
    }
}
