package com.example.attendanceapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import java.util.List;

public class EventDecorator implements DayViewDecorator {
    private final Drawable highlightDrawable;
    private final List<CalendarDay> dates;

    public EventDecorator(Integer color, Context context, List<CalendarDay> dates) {
        this.highlightDrawable = ContextCompat.getDrawable(context, color);
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(com.prolificinteractive.materialcalendarview.DayViewFacade view) {
        view.setBackgroundDrawable(highlightDrawable);
    }
}
