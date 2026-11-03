"use client";

import * as React from "react";
import { createPortal } from "react-dom";
import { format } from "date-fns";
import { CalendarDays, ChevronDown, X } from "lucide-react";
import { DayPicker, useDayPicker, type DropdownProps } from "react-day-picker";
import "react-day-picker/dist/style.css";
import { cn } from "@/lib/utils";

function isYearOptionList(options: DropdownProps["options"]) {
  return /^\d{4}$/.test(String(options?.[0]?.label ?? ""));
}

/** Month grid and year grid used only when a DatePicker opts into `modernCaption`. */
function ModernCaptionDropdown({ options, value, disabled, "aria-label": ariaLabel }: DropdownProps) {
  const { months, goToMonth } = useDayPicker();
  const [open, setOpen] = React.useState(false);
  const rootRef = React.useRef<HTMLDivElement>(null);
  const selectedRef = React.useRef<HTMLButtonElement>(null);
  const yearList = isYearOptionList(options);
  const selected = options?.find((option) => String(option.value) === String(value));

  React.useEffect(() => {
    if (!open) return;
    selectedRef.current?.scrollIntoView({ block: "nearest" });
    function handle(e: MouseEvent) {
      if (!rootRef.current?.contains(e.target as Node)) setOpen(false);
    }
    document.addEventListener("mousedown", handle);
    return () => document.removeEventListener("mousedown", handle);
  }, [open]);

  const choose = (next: number) => {
    const displayed = months[0]?.date ?? new Date();
    const year = yearList ? next : displayed.getFullYear();
    const month = yearList ? displayed.getMonth() : next;
    goToMonth(new Date(year, month, 1));
    setOpen(false);
  };

  return (
    <div ref={rootRef} className="relative min-w-0 flex-1">
      <button
        type="button"
        disabled={disabled}
        aria-label={ariaLabel}
        aria-expanded={open}
        onClick={() => setOpen((current) => !current)}
        className={cn(
          "flex w-full items-center justify-between gap-1 rounded-xl border bg-white px-2.5 py-1.5 text-xs font-semibold text-slate-800 shadow-sm transition",
          open ? "border-violet-400 ring-2 ring-violet-500/20" : "border-slate-200 hover:border-violet-300",
          disabled && "cursor-not-allowed opacity-50"
        )}
      >
        <span className="truncate">{selected?.label}</span>
        <ChevronDown className={cn("h-3.5 w-3.5 shrink-0 text-slate-400 transition-transform", open && "rotate-180")} />
      </button>
      {open && (
        <div
          className={cn(
            "absolute top-full z-30 mt-1.5 rounded-2xl border border-slate-200/80 bg-white p-2 shadow-[0_16px_40px_-12px_rgba(15,23,42,0.28)] ring-1 ring-black/[0.04]",
            yearList ? "right-0 w-[calc(200%+0.5rem)]" : "left-0 w-[calc(200%+0.5rem)]"
          )}
        >
          <div
            className={cn(
              "grid grid-cols-3 gap-1",
              yearList && "max-h-40 overflow-y-auto overscroll-contain pr-0.5 [scrollbar-width:thin]"
            )}
          >
            {options?.map((option) => {
              const active = String(option.value) === String(value);
              return (
                <button
                  key={option.value}
                  ref={active ? selectedRef : undefined}
                  type="button"
                  disabled={option.disabled}
                  onClick={() => choose(option.value)}
                  className={cn(
                    "rounded-lg px-1 py-1.5 text-[11px] font-medium transition",
                    active
                      ? "bg-gradient-to-br from-violet-600 to-indigo-600 text-white shadow-sm shadow-violet-500/30"
                      : "text-slate-700 hover:bg-violet-50 hover:text-violet-800",
                    option.disabled && "cursor-not-allowed text-slate-300 line-through hover:bg-transparent"
                  )}
                >
                  {yearList ? option.label : option.label.slice(0, 3)}
                </button>
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
}

interface DatePickerProps {
  value?: string; // yyyy-MM-dd
  onChange?: (value: string) => void;
  placeholder?: string;
  required?: boolean;
  disabled?: boolean;
  label?: string;
  minDate?: Date;
  maxDate?: Date;
  /** Compact trigger + panel for tables and dense layouts */
  variant?: "default" | "compact";
  /** Popover opens toward end (right) — useful for last columns in wide tables */
  alignPopover?: "start" | "end";
  /** Month and year lists render as grids instead of the browser’s native menus. */
  modernCaption?: boolean;
}

export function DatePicker({
  value,
  onChange,
  placeholder = "Pick a date",
  required,
  disabled,
  label,
  minDate,
  maxDate,
  variant = "default",
  alignPopover = "start",
  modernCaption = false,
}: DatePickerProps) {
  const [open, setOpen] = React.useState(false);
  const [mounted, setMounted] = React.useState(false);
  const ref = React.useRef<HTMLDivElement>(null);
  const panelRef = React.useRef<HTMLDivElement>(null);
  const [panelPos, setPanelPos] = React.useState({ top: 0, left: 0, width: 300 });
  const compact = variant === "compact";
  const panelWidth = compact ? 272 : 300;

  const selected = value ? new Date(value + "T00:00:00") : undefined;

  React.useEffect(() => setMounted(true), []);

  const updatePanelPosition = React.useCallback(() => {
    if (!ref.current) return;
    const r = ref.current.getBoundingClientRect();
    const left =
      alignPopover === "end" ? Math.max(8, r.right - panelWidth) : Math.max(8, r.left);
    setPanelPos({
      top: r.bottom + 8,
      left,
      width: panelWidth,
    });
  }, [alignPopover, panelWidth]);

  React.useLayoutEffect(() => {
    if (!open) return;
    updatePanelPosition();
  }, [open, updatePanelPosition]);

  React.useEffect(() => {
    if (!open) return;
    const onScrollOrResize = () => updatePanelPosition();
    window.addEventListener("scroll", onScrollOrResize, true);
    window.addEventListener("resize", onScrollOrResize);
    return () => {
      window.removeEventListener("scroll", onScrollOrResize, true);
      window.removeEventListener("resize", onScrollOrResize);
    };
  }, [open, updatePanelPosition]);

  React.useEffect(() => {
    function handle(e: MouseEvent) {
      const t = e.target as Node;
      if (ref.current?.contains(t)) return;
      if (panelRef.current?.contains(t)) return;
      setOpen(false);
    }
    document.addEventListener("mousedown", handle);
    return () => document.removeEventListener("mousedown", handle);
  }, []);

  const handleSelect = (day: Date | undefined) => {
    if (!day) return;
    onChange?.(format(day, "yyyy-MM-dd"));
    setOpen(false);
  };

  const handleClear = (e: React.MouseEvent) => {
    e.stopPropagation();
    onChange?.("");
  };

  return (
    <div className={cn(!compact && "space-y-1.5")}>
      {label && (
        <label className="block text-xs font-semibold text-slate-600">
          {label}
          {required && <span className="text-red-500 ml-0.5">*</span>}
        </label>
      )}
      <div className="relative" ref={ref}>
        <button
          type="button"
          disabled={disabled}
          onClick={() => setOpen((v) => !v)}
          className={cn(
            "w-full flex items-center gap-2 text-left transition-all duration-200",
            "border bg-white/90 backdrop-blur-sm",
            "shadow-sm hover:shadow-md",
            compact
              ? "px-2.5 py-1.5 rounded-lg text-xs min-w-[132px] gap-1.5"
              : "px-3.5 py-2.5 rounded-xl text-sm",
            disabled ? "opacity-50 cursor-not-allowed bg-slate-100" : "hover:border-violet-400/80 cursor-pointer",
            open
              ? "border-violet-500 ring-2 ring-violet-500/20 shadow-md"
              : "border-slate-200/90 hover:border-violet-300",
            !selected ? "text-slate-400" : "text-slate-800"
          )}
        >
          <span
            className={cn(
              "flex items-center justify-center rounded-full shrink-0",
              compact ? "w-6 h-6 bg-gradient-to-br from-violet-500 to-indigo-600" : "w-8 h-8 bg-gradient-to-br from-violet-500 to-indigo-600"
            )}
          >
            <CalendarDays className={cn("text-white", compact ? "w-3 h-3" : "w-4 h-4")} />
          </span>
          <span className="flex-1 truncate font-medium">
            {selected ? format(selected, compact ? "dd MMM yyyy" : "dd MMM yyyy") : placeholder}
          </span>
          {selected && !disabled && (
            <X
              onClick={handleClear}
              className={cn(
                "text-slate-300 hover:text-slate-600 transition shrink-0 rounded-md hover:bg-slate-100 p-0.5",
                compact ? "w-3.5 h-3.5" : "w-4 h-4"
              )}
            />
          )}
        </button>

        {open &&
          mounted &&
          createPortal(
            <div
              ref={panelRef}
              data-date-picker-panel
              className="fixed z-[200] animate-in fade-in zoom-in-95 duration-150"
              style={{
                top: panelPos.top,
                left: panelPos.left,
                width: panelPos.width,
                maxWidth: "min(100vw - 16px, 340px)",
              }}
            >
              <div
                className={cn(
                  "rounded-2xl border border-slate-200/80 bg-white shadow-[0_20px_50px_-12px_rgba(0,0,0,0.18)] overflow-hidden",
                  "ring-1 ring-black/[0.04]",
                  compact ? "min-w-[272px]" : "min-w-[300px]"
                )}
              >
                <div className="bg-gradient-to-r from-violet-600/10 via-indigo-500/5 to-transparent px-3 py-2 border-b border-slate-100/80">
                  <p className="text-[11px] font-semibold uppercase tracking-wider text-violet-700/90">Select date</p>
                </div>
                <DayPicker
                  mode="single"
                  selected={selected}
                  onSelect={handleSelect}
                  captionLayout="dropdown"
                  startMonth={minDate ?? new Date(2015, 0, 1)}
                  endMonth={maxDate ?? new Date(2030, 11, 31)}
                  disabled={
                    minDate || maxDate
                      ? [
                          ...(minDate ? [{ before: minDate }] : []),
                          ...(maxDate ? [{ after: maxDate }] : []),
                        ]
                      : undefined
                  }
                  showOutsideDays
                  components={modernCaption ? { Dropdown: ModernCaptionDropdown } : undefined}
                  className={cn(
                    compact ? "p-2.5" : "p-3",
                    compact
                      ? "[--rdp-day-height:2rem] [--rdp-day-width:2rem] [--rdp-day_button-height:2rem] [--rdp-day_button-width:2rem]"
                      : "[--rdp-day-height:2.25rem] [--rdp-day-width:2.25rem] [--rdp-day_button-height:2.25rem] [--rdp-day_button-width:2.25rem]",
                    "[--rdp-day_button-border:2px_solid_transparent] [--rdp-selected-border:2px_solid_transparent]",
                    "[&_.rdp-selected]:text-[length:inherit] [&_.rdp-selected]:font-medium"
                  )}
                  classNames={{
                    months: "flex flex-col sm:flex-row gap-4",
                    month: "space-y-2 w-full",
                    month_caption: "flex justify-center items-center relative px-1 py-1 mb-1",
                    caption_label: "hidden",
                    dropdowns: "flex gap-2 items-center justify-center w-full mb-2",
                    dropdown: cn(
                      "rounded-lg border border-slate-200 bg-slate-50/80 text-slate-800 text-xs font-medium py-1.5 px-2 cursor-pointer hover:border-violet-300 transition",
                      "focus:outline-none focus:ring-2 focus:ring-violet-500/30"
                    ),
                    vhidden: "hidden",
                    nav: "hidden",
                    month_grid: "w-full border-collapse",
                    weekdays: "flex w-full",
                    weekday: cn(
                      "flex-1 text-slate-400 font-semibold text-center uppercase tracking-wide",
                      compact ? "text-[0.65rem] pb-1" : "text-[0.8rem]"
                    ),
                    week: "flex w-full mt-0.5",
                    day: "relative flex flex-1 items-center justify-center p-0",
                    day_button: cn(
                      "inline-flex items-center justify-center rounded-full p-0 font-medium leading-none transition-colors",
                      "text-slate-700 hover:bg-violet-100 hover:text-violet-900",
                      "focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-violet-500/40",
                      compact ? "size-8 text-xs" : "size-9 text-sm"
                    ),
                    selected:
                      "[&>button]:bg-gradient-to-br [&>button]:from-violet-600 [&>button]:to-indigo-600 [&>button]:text-white [&>button]:hover:from-violet-600 [&>button]:hover:to-indigo-600 [&>button]:hover:text-white [&>button]:font-semibold [&>button]:shadow-md [&>button]:shadow-violet-500/25",
                    today:
                      "[&>button]:ring-2 [&>button]:ring-inset [&>button]:ring-violet-400/70 [&>button]:font-semibold [&>button]:text-violet-900",
                    outside: "text-slate-300 opacity-60",
                    disabled: "text-slate-300 opacity-40 line-through",
                  }}
                />
              </div>
            </div>,
            document.body
          )}
      </div>
    </div>
  );
}
