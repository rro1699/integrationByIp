package com.example.integratorbyip.front;

import com.example.integratorbyip.Entity.IpInfo;
import com.example.integratorbyip.Services.IpService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "data")
public class GetDataView extends VerticalLayout {
    private TextField filterTextIP;
    private DatePicker filterDate;
    private Grid<IpInfo> grid;
    private IpService ipService;

    public GetDataView(IpService ipService) {
        this.ipService = ipService;
        configGrid();
        add(getToolbar(),grid);
    }

    private void configGrid(){
        grid = new Grid();
        grid.addColumn(IpInfo::getIp).setHeader("Ip");
        grid.addColumn(IpInfo::getDate).setHeader("Дата");
        grid.addColumn(IpInfo::getLocation).setHeader("Локация");
        grid.addColumn(IpInfo::getLatitude).setHeader("Широта");
        grid.addColumn(IpInfo::getLongitude).setHeader("Долгота");
        grid.addColumn(IpInfo::getApp_temp).setHeader("Ощущаемая температура");
        grid.addColumn(IpInfo::getClouds).setHeader("Облачность");
        grid.setHeightByRows(true);
        grid.setItems(ipService.findAllRecords("",""));
    }

    private HorizontalLayout getToolbar() {
        filterTextIP = new TextField();
        filterTextIP.setLabel("Filter by IP");
        filterTextIP.setPlaceholder("255.255.255.255");
        filterTextIP.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        filterTextIP.addValueChangeListener(e -> updateByIP());


        filterDate= new DatePicker();
        filterDate.setLabel("Filtering by date");
        filterDate.addValueChangeListener(e -> updateByDate());


        HorizontalLayout toolbar = new HorizontalLayout(filterTextIP, filterDate);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateByDate() {
        LocalDate localDate = filterDate.getValue();
        if (localDate==null){
            grid.setItems(ipService.findAllRecords("Date", ""));
        }else{
            grid.setItems(ipService.findAllRecords("Date", localDate.toString()));
        }
    }

    private void updateByIP(){
        grid.setItems(ipService.findAllRecords("Ip",filterTextIP.getValue()));

    }
}
