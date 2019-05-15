package ir.asta.training.contacts.manager;

import ir.asta.training.contacts.entities.TimeEntity;
import ir.asta.training.contacts.entities.BookEntity;

import javax.inject.Named;

@Named("sampleManager")
public class SampleManager {

    public BookEntity book(BookEntity entity){
        entity.setComment(entity.getBookname() + " written by " + entity.getAuthor());
        entity.setAuthor("Mr. " + entity.getAuthor());
        entity.setBookname(entity.getBookname() + ", a novel");
        return entity;
    }

    public TimeEntity time(int year, int month, int day){
        TimeEntity entity = new TimeEntity();
        entity.setYear(1377);
        entity.setMonth(6);
        entity.setDay(16);
        return entity;
    }
}
