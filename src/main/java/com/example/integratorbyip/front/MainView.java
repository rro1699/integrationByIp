package com.example.integratorbyip.front;


import com.example.integratorbyip.Entity.Weather;
import com.example.integratorbyip.Services.LocationByIPService;
import com.example.integratorbyip.Services.PictureByNASAService;
import com.example.integratorbyip.Services.WeatherByIPService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.example.integratorbyip.Entity.Location;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.WebBrowser;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URL;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Route("")
public class MainView extends VerticalLayout {
    private Grid<Location> locationGrid;
    private Grid<Weather> weatherGrid;
    private Image image;
    private LocationByIPService locationByIPService;
    private PictureByNASAService pictureByNASAService;
    private WeatherByIPService weatherByIPService;


    public MainView(LocationByIPService locationByIPService, PictureByNASAService pictureByNASAService,
                    WeatherByIPService weatherByIPService){
        this.locationByIPService = locationByIPService;
        this.pictureByNASAService = pictureByNASAService;
        this.weatherByIPService = weatherByIPService;
        locationGrid = new Grid();
        weatherGrid = new Grid();
        getDataForCreateForm();
    }

    private void getDataForCreateForm(){
        final WebBrowser webBrowser = UI.getCurrent().getSession().getBrowser();
        System.out.println("localhost: " + webBrowser.getAddress());
        String remoteAddr = locationByIPService.getIpClient(webBrowser);
        Location location = locationByIPService.getInfoAboutLocationByIp(remoteAddr);
        Weather weather = weatherByIPService.getWeather(location.getLatitude(),location.getLongitude());
        URL pathImage = pictureByNASAService.getPicture();
        String title = pictureByNASAService.getTitle();
        build(location,weather,pathImage,title);
    }

    public void build(Location location, Weather weather, URL imagePath, String tile){

        locationGrid.addColumn(Location::getLocation).setHeader("Локация");
        locationGrid.addColumn(Location::getIp).setHeader("Ip");
        locationGrid.addColumn(Location::getLatitude).setHeader("Широта");
        locationGrid.addColumn(Location::getLongitude).setHeader("Долгота");
        locationGrid.setItems(location);
        locationGrid.setHeightByRows(true);


        weatherGrid.addColumn(Weather::getCity_name).setHeader("Название города");
        weatherGrid.addColumn(Weather::getApp_temp).setHeader("Ощущаемая температура");
        weatherGrid.addColumn(Weather::getClouds).setHeader("Облачность");
        weatherGrid.addColumn(Weather::getWind_spd).setHeader("Скорость ветра");
        weatherGrid.addColumn(Weather::getWind_cdir_full).setHeader("Направление ветра");
        weatherGrid.addColumn(Weather::getSunrise).setHeader("Время восхода");
        weatherGrid.addColumn(Weather::getSunset).setHeader("Время заката");
        weatherGrid.addColumn(Weather::getRh).setHeader("Относительная влажность");
        weatherGrid.addColumn(Weather::getAqi).setHeader("Индекс качества воздуха");
        weatherGrid.setItems(weather);
        weatherGrid.setHeightByRows(true);

        image = new Image();
        image.setSrc(imagePath.toString());
        image.setAlt(tile);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setJustifyContentMode ( FlexComponent.JustifyContentMode.CENTER );
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(locationGrid,weatherGrid,image);
    }
}
