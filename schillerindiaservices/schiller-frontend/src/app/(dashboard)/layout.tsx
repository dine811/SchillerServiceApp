import { Navbar } from "@/components/layout/navbar";
import { Sidebar } from "@/components/layout/sidebar";
import { NavSectionsProvider } from "@/contexts/nav-sections-context";

const DashboardLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <NavSectionsProvider>
      <div className="relative min-h-screen overflow-x-hidden bg-slate-50/40">
        <aside className="fixed inset-y-0 left-0 z-[80] hidden h-full w-64 flex-col overflow-hidden md:flex">
          <Sidebar />
        </aside>
        <main className="flex min-h-screen min-w-0 flex-col md:pl-64">
          <Navbar />
          <div className="min-w-0 flex-1 p-6">{children}</div>
        </main>
      </div>
    </NavSectionsProvider>
  );
};

export default DashboardLayout;
