package com.example.salonapp.presentation.components.Schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salonapp.R
import com.example.salonapp.common.Constants
import com.example.salonapp.domain.models.Booking
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.IsoFields
import kotlin.math.roundToInt


//This file is based on the library by Daniel Rampelt
//from the following git repo: https://github.com/drampelt/WeekSchedule





class SplitType private constructor(val value: Int) {
    companion object {
        val None = SplitType(0)
        val Start = SplitType(1)
        val End = SplitType(2)
        val Both = SplitType(3)
    }
}

data class PositionedBooking(
    val booking: Booking,
    val splitType: SplitType,
    val date: LocalDate,
    val start: LocalTime,
    val end: LocalTime,
    val col: Int = 0,
    val colSpan: Int = 1,
    val colTotal: Int = 1,
)

val BookingTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun BasicBooking(
    positionedBooking: PositionedBooking,
    modifier: Modifier = Modifier,
) {
    val booking = positionedBooking.booking
    val topRadius = if (positionedBooking.splitType == SplitType.Start || positionedBooking.splitType == SplitType.Both) 0.dp else 4.dp
    val bottomRadius = if (positionedBooking.splitType == SplitType.End || positionedBooking.splitType == SplitType.Both) 0.dp else 4.dp
    val color = if(booking.bookedBy.role == Constants.ROLE_CUSTOMER) Color(0xFF1B998B) else Color(
        0xFF65991B
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                end = 2.dp,
                bottom = if (positionedBooking.splitType == SplitType.End) 0.dp else 2.dp
            )
            .clipToBounds()
            .background(
                color,
                shape = RoundedCornerShape(
                    topStart = topRadius,
                    topEnd = topRadius,
                    bottomEnd = bottomRadius,
                    bottomStart = bottomRadius,
                )
            )
            .padding(4.dp)
    ) {
        Text(
            text = "${booking.startTime.format(BookingTimeFormatter)} - ${booking.endTime.format(
                BookingTimeFormatter
            )}",
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )

        Text(
            text = booking.service.name,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (booking.note != null) {
            Text(
                text = booking.note,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


private class BookingDataModifier(
    val positionedBooking: PositionedBooking,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = positionedBooking
}

private fun Modifier.eventData(positionedBooking: PositionedBooking) = this.then(BookingDataModifier(positionedBooking))

private val DayFormatter = DateTimeFormatter.ofPattern("EE, MMM d")

@Composable
fun BasicDayHeader(
    day: LocalDate,
    modifier: Modifier = Modifier,
) {
    Text(
        text = day.format(DayFormatter),
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}


@Composable
fun ScheduleHeader(
    minDate: LocalDate,
    maxDate: LocalDate,
    dayWidth: Dp,
    modifier: Modifier = Modifier,
    dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) },
) {
    Row(modifier = modifier) {
        val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
        repeat(numDays) { i ->
            Box(modifier = Modifier.width(dayWidth)) {
                dayHeader(minDate.plusDays(i.toLong()))
            }
        }
    }
}


private val HourFormatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun BasicSidebarLabel(
    time: LocalTime,
    modifier: Modifier = Modifier,
) {
    Text(
        text = time.format(HourFormatter),
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
    )
}


@Composable
fun ScheduleSidebar(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    label: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) },
) {
    val numMinutes = ChronoUnit.MINUTES.between(minTime, maxTime).toInt() + 1
    val numHours = numMinutes / 60
    val firstHour = minTime.truncatedTo(ChronoUnit.HOURS)
    val firstHourOffsetMinutes = if (firstHour == minTime) 0 else ChronoUnit.MINUTES.between(minTime, firstHour.plusHours(1))
    val firstHourOffset = hourHeight * (firstHourOffsetMinutes / 60f)
    val startTime = if (firstHour == minTime) firstHour else firstHour.plusHours(1)
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(firstHourOffset))
        repeat(numHours) { i ->
            Box(modifier = Modifier.height(hourHeight)) {
                label(startTime.plusHours(i.toLong()))
            }
        }
    }
}


private fun splitBookings(bookings: List<Booking>): List<PositionedBooking> {
    return bookings
        .map { booking ->
            val startDate = booking.startTime.toLocalDate()
            val endDate = booking.endTime.toLocalDate()
            if (startDate == endDate) {
                listOf(PositionedBooking(booking, SplitType.None, booking.startTime.toLocalDate(), booking.startTime.toLocalTime(), booking.endTime.toLocalTime()))
            } else {
                val days = ChronoUnit.DAYS.between(startDate, endDate)
                val splitBookings = mutableListOf<PositionedBooking>()
                for (i in 0..days) {
                    val date = startDate.plusDays(i)
                    splitBookings += PositionedBooking(
                        booking,
                        splitType = if (date == startDate) SplitType.End else if (date == endDate) SplitType.Start else SplitType.Both,
                        date = date,
                        start = if (date == startDate) booking.startTime.toLocalTime() else LocalTime.MIN,
                        end = if (date == endDate) booking.endTime.toLocalTime() else LocalTime.MAX,
                    )
                }
                splitBookings
            }
        }
        .flatten()
}

private fun PositionedBooking.overlapsWith(other: PositionedBooking): Boolean {
    return date == other.date && start < other.end && end > other.start
}

private fun List<PositionedBooking>.timesOverlapWith(booking: PositionedBooking): Boolean {
    return any { it.overlapsWith(booking) }
}

private fun arrangeBookings(bookings: List<PositionedBooking>): List<PositionedBooking> {
    val positionedBookings = mutableListOf<PositionedBooking>()
    val groupBookings: MutableList<MutableList<PositionedBooking>> = mutableListOf()

    fun resetGroup() {
        groupBookings.forEachIndexed { colIndex, col ->
            col.forEach { e ->
                positionedBookings.add(e.copy(col = colIndex, colTotal = groupBookings.size))
            }
        }
        groupBookings.clear()
    }

    bookings.forEach { booking ->
        var firstFreeCol = -1
        var numFreeCol = 0
        for (i in 0 until groupBookings.size) {
            val col = groupBookings[i]
            if (col.timesOverlapWith(booking)) {
                if (firstFreeCol < 0) continue else break
            }
            if (firstFreeCol < 0) firstFreeCol = i
            numFreeCol++
        }

        when {
            // Overlaps with all, add a new column
            firstFreeCol < 0 -> {
                groupBookings += mutableListOf(booking)
                // Expand anything that spans into the previous column and doesn't overlap with this booking
                for (ci in 0 until groupBookings.size - 1) {
                    val col = groupBookings[ci]
                    col.forEachIndexed { ei, e ->
                        if (ci + e.colSpan == groupBookings.size - 1 && !e.overlapsWith(booking)) {
                            col[ei] = e.copy(colSpan = e.colSpan + 1)
                        }
                    }
                }
            }
            // No overlap with any, start a new group
            numFreeCol == groupBookings.size -> {
                resetGroup()
                groupBookings += mutableListOf(booking)
            }
            // At least one column free, add to first free column and expand to as many as possible
            else -> {
                groupBookings[firstFreeCol] += booking.copy(colSpan = numFreeCol)
            }
        }
    }
    resetGroup()
    return positionedBookings
}

sealed class ScheduleSize {
    class FixedSize(val size: Dp) : ScheduleSize()
    class FixedCount(val count: Float) : ScheduleSize() {
        constructor(count: Int) : this(count.toFloat())
    }
    class Adaptive(val minSize: Dp) : ScheduleSize()
}

@Composable
fun Schedule(
    bookings: List<Booking>,
    onCreateBook: () -> Unit,
    onWeekChanged: (newWeek: LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    bookingContent: @Composable (positionedBooking: PositionedBooking) -> Unit = { BasicBooking(positionedBooking = it) },
    dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) },
    timeLabel: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) },
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    daySize: ScheduleSize = ScheduleSize.FixedSize(256.dp),
    hourSize: ScheduleSize = ScheduleSize.FixedSize(64.dp),
    viewModel: ScheduleViewModel = hiltViewModel()
) {

    val minDate = viewModel.state.value.currentWeek.with(DayOfWeek.MONDAY).toLocalDate()?: LocalDate.now()
    val maxDate = viewModel.state.value.currentWeek.plusDays(6).toLocalDate() ?: LocalDate.now()

    val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    val numMinutes = ChronoUnit.MINUTES.between(minTime, maxTime).toInt() + 1
    val numHours = numMinutes.toFloat() / 60f
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    var sidebarWidth by remember { mutableStateOf(0) }
    var headerHeight by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(ScheduleEvent.OnPreviousWeek)
                        onWeekChanged(viewModel.state.value.currentWeek)
                    }
                ) {
                    Icon(Icons.Filled.ArrowLeft, contentDescription = stringResource(R.string.previous_week))
                }

                //val week = CalendarUtils.getWeekNumberAsString(viewModel.state.value.currentWeek)
                //val customWeekFields = IsoFields.of(DayOfWeek.MONDAY, 7);
                val week = viewModel.state.value.currentWeek.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)

                Text(text =
                stringResource(R.string.week) + " " + week + " - " + viewModel.state.value.currentWeek.year,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)

                IconButton(
                    onClick = {
                        viewModel.onEvent(ScheduleEvent.OnNextWeek)
                        onWeekChanged(viewModel.state.value.currentWeek)
                    }
                ) {
                    Icon(Icons.Filled.ArrowRight, contentDescription = stringResource(R.string.next_week))
                }
            }

            BoxWithConstraints(modifier = modifier) {
                val dayWidth: Dp = when (daySize) {
                    is ScheduleSize.FixedSize -> daySize.size
                    is ScheduleSize.FixedCount -> with(LocalDensity.current) { ((constraints.maxWidth - sidebarWidth) / daySize.count).toDp() }
                    is ScheduleSize.Adaptive -> with(LocalDensity.current) { maxOf(((constraints.maxWidth - sidebarWidth) / numDays).toDp(), daySize.minSize) }
                }
                val hourHeight: Dp = when (hourSize) {
                    is ScheduleSize.FixedSize -> hourSize.size
                    is ScheduleSize.FixedCount -> with(LocalDensity.current) { ((constraints.maxHeight - headerHeight) / hourSize.count).toDp() }
                    is ScheduleSize.Adaptive -> with(LocalDensity.current) { maxOf(((constraints.maxHeight - headerHeight) / numHours).toDp(), hourSize.minSize) }
                }
                Column(modifier = modifier) {
                    ScheduleHeader(
                        minDate = minDate,
                        maxDate = maxDate,
                        dayWidth = dayWidth,
                        dayHeader = dayHeader,
                        modifier = Modifier
                            .padding(start = with(LocalDensity.current) { sidebarWidth.toDp() })
                            .horizontalScroll(horizontalScrollState)
                            .onGloballyPositioned { headerHeight = it.size.height }
                    )
                    Row(modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Start)) {
                        ScheduleSidebar(
                            hourHeight = hourHeight,
                            minTime = minTime,
                            maxTime = maxTime,
                            label = timeLabel,
                            modifier = Modifier
                                .verticalScroll(verticalScrollState)
                                .onGloballyPositioned { sidebarWidth = it.size.width }
                        )
                        BasicSchedule(
                            bookings = bookings,
                            bookingContent = bookingContent,
                            minDate = minDate,
                            maxDate = maxDate,
                            minTime = minTime,
                            maxTime = maxTime,
                            dayWidth = dayWidth,
                            hourHeight = hourHeight,
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(verticalScrollState)
                                .horizontalScroll(horizontalScrollState)
                        )
                    }
                }
            }
        }

        ExtendedFloatingActionButton(
            onClick = { onCreateBook() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        ) {
            Icon(Icons.Filled.Create, stringResource(R.string.add_booking))
            Text(text = stringResource(R.string.add_booking))
        }

    }





}

@Composable
fun BasicSchedule(
    bookings: List<Booking>,
    modifier: Modifier = Modifier,
    bookingContent: @Composable (positionedBooking: PositionedBooking) -> Unit = { BasicBooking(positionedBooking = it) },
    minDate: LocalDate = bookings.minByOrNull(Booking::startTime)?.startTime?.toLocalDate() ?: LocalDate.now(),
    maxDate: LocalDate = bookings.maxByOrNull(Booking::endTime)?.endTime?.toLocalDate() ?: LocalDate.now(),
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    dayWidth: Dp,
    hourHeight: Dp,
) {
    val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    val numMinutes = ChronoUnit.MINUTES.between(minTime, maxTime).toInt() + 1
    val numHours = numMinutes / 60
    val dividerColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray
    val positionedBookings = remember(bookings) { arrangeBookings(splitBookings(bookings.sortedBy(Booking::startTime))).filter { it.end > minTime && it.start < maxTime } }
    Layout(
        content = {
            positionedBookings.forEach { positionedBooking ->
                Box(modifier = Modifier.eventData(positionedBooking)) {
                    bookingContent(positionedBooking)
                }
            }
        },
        modifier = modifier
            .drawBehind {
                val firstHour = minTime.truncatedTo(ChronoUnit.HOURS)
                val firstHourOffsetMinutes = if (firstHour == minTime) 0 else ChronoUnit.MINUTES.between(minTime, firstHour.plusHours(1))
                val firstHourOffset = (firstHourOffsetMinutes / 60f) * hourHeight.toPx()
                repeat(numHours) {
                    drawLine(
                        dividerColor,
                        start = Offset(0f, it * hourHeight.toPx() + firstHourOffset),
                        end = Offset(size.width, it * hourHeight.toPx() + firstHourOffset),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                repeat(numDays - 1) {
                    drawLine(
                        dividerColor,
                        start = Offset((it + 1) * dayWidth.toPx(), 0f),
                        end = Offset((it + 1) * dayWidth.toPx(), size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) { measureables, constraints ->
        val height = (hourHeight.toPx() * (numMinutes / 60f)).roundToInt()
        val width = dayWidth.roundToPx() * numDays
        val placeablesWithBookings = measureables.map { measurable ->
            val splitBooking = measurable.parentData as PositionedBooking
            val bookingDurationMinutes = ChronoUnit.MINUTES.between(splitBooking.start, minOf(splitBooking.end, maxTime))
            val bookingHeight = ((bookingDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
            val bookingWidth = ((splitBooking.colSpan.toFloat() / splitBooking.colTotal.toFloat()) * dayWidth.toPx()).roundToInt()
            val placeable = measurable.measure(constraints.copy(minWidth = bookingWidth, maxWidth = bookingWidth, minHeight = bookingHeight, maxHeight = bookingHeight))
            Pair(placeable, splitBooking)
        }
        layout(width, height) {
            placeablesWithBookings.forEach { (placeable, splitBooking) ->
                val bookingOffsetMinutes = if (splitBooking.start > minTime) ChronoUnit.MINUTES.between(minTime, splitBooking.start) else 0
                val bookingY = ((bookingOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val bookingOffsetDays = ChronoUnit.DAYS.between(minDate, splitBooking.date).toInt()
                val bookingX = bookingOffsetDays * dayWidth.roundToPx() + (splitBooking.col * (dayWidth.toPx() / splitBooking.colTotal.toFloat())).roundToInt()
                placeable.place(bookingX, bookingY)
            }
        }
    }
}