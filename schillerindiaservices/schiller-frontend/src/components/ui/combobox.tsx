"use client";

import * as React from "react";
import { Check, ChevronDown, Search, X } from "lucide-react";

interface ComboboxProps {
  options: { value: string; label: string }[];
  value?: string;
  onChange?: (value: string) => void;
  placeholder?: string;
  searchPlaceholder?: string;
  required?: boolean;
  disabled?: boolean;
  label?: string;
  accentColor?: string; // tailwind ring color, e.g. "violet"
}

export function Combobox({
  options,
  value,
  onChange,
  placeholder = "Select...",
  searchPlaceholder = "Search...",
  required,
  disabled,
  label,
  accentColor = "violet",
}: ComboboxProps) {
  const [open, setOpen] = React.useState(false);
  const [query, setQuery] = React.useState("");
  const ref = React.useRef<HTMLDivElement>(null);

  React.useEffect(() => {
    function handle(e: MouseEvent) {
      if (ref.current && !ref.current.contains(e.target as Node)) {
        setOpen(false);
        setQuery("");
      }
    }
    document.addEventListener("mousedown", handle);
    return () => document.removeEventListener("mousedown", handle);
  }, []);

  const filtered = query
    ? options.filter(o => o.label.toLowerCase().includes(query.toLowerCase()))
    : options;

  const selected = options.find(o => o.value === value);

  const handleSelect = (v: string) => {
    onChange?.(v === value ? "" : v);
    setOpen(false);
    setQuery("");
  };

  const handleClear = (e: React.MouseEvent) => {
    e.stopPropagation();
    onChange?.("");
  };

  const accentRing = `ring-${accentColor}-100 border-${accentColor}-400`;

  return (
    <div className="space-y-1.5">
      {label && (
        <label className="block text-xs font-semibold text-slate-600">
          {label}{required && <span className="text-red-500 ml-0.5">*</span>}
        </label>
      )}
      <div className="relative" ref={ref}>
        <button
          type="button"
          disabled={disabled}
          onClick={() => { setOpen(v => !v); setQuery(""); }}
          className={`w-full flex items-center gap-2 px-3.5 py-2.5 rounded-xl border text-sm text-left transition-all
            ${disabled ? "opacity-50 cursor-not-allowed bg-slate-100 border-slate-200" : "bg-white hover:border-violet-400 cursor-pointer border-slate-200"}
            ${open ? `border-violet-400 ring-2 ring-violet-100` : ""}
            ${!selected ? "text-slate-400" : "text-slate-800 font-medium"}`}
        >
          <span className="flex-1 truncate">{selected ? selected.label : placeholder}</span>
          {selected && !disabled && (
            <X onClick={handleClear} className="w-3.5 h-3.5 text-slate-300 hover:text-slate-500 transition shrink-0" />
          )}
          <ChevronDown className={`w-4 h-4 text-slate-400 transition-transform shrink-0 ${open ? "rotate-180" : ""}`} />
        </button>

        {open && (
          <div className="absolute z-50 w-full mt-1.5 bg-white border border-slate-200 rounded-2xl shadow-xl overflow-hidden">
            <div className="flex items-center gap-2 px-3 py-2.5 border-b border-slate-100">
              <Search className="w-3.5 h-3.5 text-slate-400 shrink-0" />
              <input
                autoFocus
                value={query}
                onChange={e => setQuery(e.target.value)}
                placeholder={searchPlaceholder}
                className="flex-1 text-sm outline-none text-slate-700 placeholder:text-slate-400 bg-transparent"
              />
            </div>
            <div className="max-h-52 overflow-y-auto">
              {filtered.length === 0 ? (
                <p className="text-xs text-slate-400 text-center py-4">No results found</p>
              ) : (
                filtered.map(opt => (
                  <button
                    key={opt.value}
                    type="button"
                    onClick={() => handleSelect(opt.value)}
                    className={`w-full flex items-center gap-2 px-4 py-2.5 text-sm text-left transition-colors
                      ${opt.value === value ? "bg-violet-50 text-violet-700 font-medium" : "text-slate-700 hover:bg-slate-50"}`}
                  >
                    <div className={`w-4 h-4 rounded-full border-2 flex items-center justify-center shrink-0
                      ${opt.value === value ? "border-violet-500 bg-violet-500" : "border-slate-300"}`}>
                      {opt.value === value && <Check className="w-2.5 h-2.5 text-white" strokeWidth={3} />}
                    </div>
                    {opt.label}
                  </button>
                ))
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
