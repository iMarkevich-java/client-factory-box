package com.markevich.factory.service.day;

import biznesObgectFactory.Day;
import biznesObgectFactory.StaffDays;
import com.markevich.factory.service.Service;

import java.util.List;

public class StaffDayServices implements Service<StaffDays> {


    @Override
    public List<StaffDays> loadAll() {
        LoadAllStaffDay loadAllStaffDay = new LoadAllStaffDay();
        return loadAllStaffDay.loadAllStaffDay();
    }

    @Override
    public StaffDays loadById(String id) {
        LoadStaffDayByID loadStaffDayByID = new LoadStaffDayByID();
        StaffDays staffDay = loadStaffDayByID.loadStaffDayByID(id);
        return staffDay;
    }

    @Override
    public void save(StaffDays staffDay) {
        SaveStaffDay addStaffDay = new SaveStaffDay();
        addStaffDay.addStaffDay(staffDay);

    }

    @Override
    public void update(StaffDays staffDay) {
        UpdateStaffDay updateStaffDay = new UpdateStaffDay();
        updateStaffDay.updateStaffDay(staffDay);
    }

    @Override
    public void delete(String id) {

    }

    public void deleteDay(Day day) {
        DeleteStaffDay deleteStaffDay = new DeleteStaffDay();
        deleteStaffDay.deleteStaffDay(day);
    }
}
