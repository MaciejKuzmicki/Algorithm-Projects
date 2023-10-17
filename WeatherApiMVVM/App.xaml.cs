using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using Microsoft.Extensions.DependencyInjection;
using Newtonsoft.Json.Bson;
using WeatherApi;
using WeatherApiMVVM.ViewModels;

namespace WeatherApiMVVM
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        IServiceProvider serviceProvider;

        public App()
        {
            var serviceCollection = new ServiceCollection();
            ConfigureServices(serviceCollection);
        }

        private void ConfigureServices(ServiceCollection serviceCollection)
        {
            serviceCollection.AddSingleton<IAccuWeatherService, AccuWeatherService>();
            serviceCollection.AddSingleton<MainViewModel>();
            serviceCollection.AddTransient<MainWindow>();
        }

        private void OnStartup(object sender, StartupEventArgs e)
        {
            var mainWindow = serviceProvider.GetService<MainWindow>();
            mainWindow.Show();
        }
    }
}
