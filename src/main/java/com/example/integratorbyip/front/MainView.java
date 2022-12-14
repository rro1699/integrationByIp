package com.example.integratorbyip.front;


import com.example.integratorbyip.Entity.IpInfo;
import com.example.integratorbyip.Entity.Weather;
import com.example.integratorbyip.Services.IpService;
import com.example.integratorbyip.Services.LocationByIPService;
import com.example.integratorbyip.Services.PictureByNASAService;
import com.example.integratorbyip.Services.WeatherByIPService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.example.integratorbyip.Entity.Location;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.WebBrowser;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Route("")
public class MainView extends VerticalLayout {
    private Grid<Location> locationGrid;
    private Grid<Weather> weatherGrid;
    private Image image;

    private LocationByIPService locationByIPService;
    private PictureByNASAService pictureByNASAService;
    private WeatherByIPService weatherByIPService;
    private IpService ipService;

    public MainView(LocationByIPService locationByIPService, PictureByNASAService pictureByNASAService,
                    WeatherByIPService weatherByIPService, IpService ipService){
        this.locationByIPService = locationByIPService;
        this.pictureByNASAService = pictureByNASAService;
        this.weatherByIPService = weatherByIPService;
        this.ipService = ipService;

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
        build(location,weather,pathImage);
        save(location,weather);
    }

    private void build(Location location, Weather weather, URL imagePath){

        locationGrid.addColumn(Location::getLocation).setHeader("??????????????");
        locationGrid.addColumn(Location::getIp).setHeader("Ip");
        locationGrid.addColumn(Location::getLatitude).setHeader("????????????");
        locationGrid.addColumn(Location::getLongitude).setHeader("??????????????");
        locationGrid.setItems(location);
        locationGrid.setHeightByRows(true);


        weatherGrid.addColumn(Weather::getCity_name).setHeader("???????????????? ????????????");
        weatherGrid.addColumn(Weather::getApp_temp).setHeader("?????????????????? ??????????????????????");
        weatherGrid.addColumn(Weather::getClouds).setHeader("????????????????????");
        weatherGrid.addColumn(Weather::getWind_spd).setHeader("???????????????? ??????????");
        weatherGrid.addColumn(Weather::getWind_cdir_full).setHeader("?????????????????????? ??????????");
        weatherGrid.addColumn(Weather::getSunrise).setHeader("?????????? ??????????????");
        weatherGrid.addColumn(Weather::getSunset).setHeader("?????????? ????????????");
        weatherGrid.addColumn(Weather::getRh).setHeader("?????????????????????????? ??????????????????");
        weatherGrid.addColumn(Weather::getAqi).setHeader("???????????? ???????????????? ??????????????");
        weatherGrid.setItems(weather);
        weatherGrid.setHeightByRows(true);

        image = new Image();
        image.setSrc(imagePath.toString());
        setJustifyContentMode(JustifyContentMode.CENTER);
        setJustifyContentMode (FlexComponent.JustifyContentMode.CENTER );
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(locationGrid,weatherGrid,image);
    }

    private void save(Location location, Weather weather){
        ipService.save(new IpInfo(location.getIp(),Date.valueOf(LocalDate.now()),location.getLocation(),
                location.getLatitude(),location.getLongitude(),weather.getApp_temp(),weather.getClouds()));
    }
}
