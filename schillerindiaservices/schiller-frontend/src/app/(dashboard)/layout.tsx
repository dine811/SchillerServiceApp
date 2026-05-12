import { Navbar } from "@/components/layout/navbar";
import { Sidebar } from "@/components/layout/sidebar";
import { NavSectionsProvider } from "@/contexts/nav-sections-context";

const DashboardLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <NavSectionsProvider>
    <div className="h-full relative">
      <div className="hidden h-full md:flex md:w-64 md:flex-col md:fixed md:inset-y-0 z-[80]">
        <Sidebar />
      </div>
      <main className="md:pl-64 min-h-screen flex flex-col">
        <Navbar />
        <div className="flex-1 p-6">
          {children}
        </div>
      </main>
    </div>
    </NavSectionsProvider>
  );
};

export default DashboardLayout;
