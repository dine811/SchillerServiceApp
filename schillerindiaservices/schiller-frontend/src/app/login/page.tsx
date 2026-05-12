"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import { defaultDashboardRouteForRole } from "@/lib/auth";
import { getApiBaseUrl, persistClientAuthToken } from "@/lib/api";

type LoginResponse = {
  accessToken: string;
  user: {
    empId: number;
    username: string;
    name: string;
    role: string;
    division?: string | null;
  };
};

export default function LoginPage() {
  const router = useRouter();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const res = await fetch(`${getApiBaseUrl()}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ username: username.trim(), password }),
      });
      const raw = (await res.text().catch(() => "")).trim();
      if (!res.ok) {
        throw new Error(raw || "Invalid username or password");
      }
      if (!raw) {
        throw new Error("Login succeeded but server returned an empty response.");
      }
      let data: LoginResponse;
      try {
        data = JSON.parse(raw) as LoginResponse;
      } catch {
        throw new Error("Invalid login response received from server.");
      }
      if (data.accessToken) {
        persistClientAuthToken(data.accessToken);
      }
      router.replace(defaultDashboardRouteForRole(data.user?.role));
      router.refresh();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="min-h-screen bg-slate-50 flex items-center justify-center p-4">
      <section className="w-full max-w-md bg-white rounded-2xl border border-slate-200 shadow-sm p-6">
        <h1 className="text-2xl font-bold text-slate-900">Sign in</h1>
        <p className="text-sm text-slate-500 mt-1">Schiller India Services</p>

        <form className="mt-6 space-y-4" onSubmit={onSubmit}>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1" htmlFor="username">
              Username
            </label>
            <input
              id="username"
              type="text"
              autoComplete="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full rounded-xl border border-slate-300 px-3 py-2 text-sm"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1" htmlFor="password">
              Password
            </label>
            <input
              id="password"
              type="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full rounded-xl border border-slate-300 px-3 py-2 text-sm"
              required
            />
          </div>
          {error && <p className="text-sm text-red-600">{error}</p>}
          <button
            type="submit"
            disabled={loading}
            className="w-full rounded-xl bg-indigo-600 text-white py-2.5 text-sm font-semibold disabled:opacity-60"
          >
            {loading ? "Signing in..." : "Sign in"}
          </button>
        </form>
      </section>
    </main>
  );
}
