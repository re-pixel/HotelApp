package models;

import java.time.LocalDate;

public class DateInterval {
	private LocalDate startDate;
    private LocalDate endDate;

    public DateInterval(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public boolean contains(LocalDate date) {
    	return (this.startDate.isBefore(date) && this.endDate.isAfter(date)) || this.startDate.equals(date) || this.endDate.equals(date);
    }

    public boolean overlaps(DateInterval other) {
        return !this.endDate.isBefore(other.startDate) && !other.endDate.isBefore(this.startDate);
    }

    public long durationDays() {
        return endDate.toEpochDay() - startDate.toEpochDay() + 1;
    }

    @Override
    public String toString() {
        return startDate.getDayOfMonth() + "/" + startDate.getMonthValue() + "/" + startDate.getYear() + "-" + endDate.getDayOfMonth() + "/" + endDate.getMonthValue() + "/" + endDate.getYear();
    }
}
