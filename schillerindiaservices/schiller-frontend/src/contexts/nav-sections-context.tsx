"use client";

import { createContext, useContext, type ReactNode } from "react";
import { usePathname } from "next/navigation";
import { useNavSectionsExpanded } from "@/hooks/use-nav-sections-expanded";

type NavSectionsApi = ReturnType<typeof useNavSectionsExpanded>;

const NavSectionsContext = createContext<NavSectionsApi | null>(null);

/** Single source of truth for sidebar + dashboard quick-nav expand/collapse (and localStorage). */
export function NavSectionsProvider({ children }: { children: ReactNode }) {
  const pathname = usePathname();
  const api = useNavSectionsExpanded(pathname);

  return (
    <NavSectionsContext.Provider value={api}>{children}</NavSectionsContext.Provider>
  );
}

export function useNavSections(): NavSectionsApi {
  const ctx = useContext(NavSectionsContext);
  if (!ctx) {
    throw new Error("useNavSections must be used within NavSectionsProvider");
  }
  return ctx;
}
