import type { NextConfig } from "next";

/** Spring Boot default; override with API_PROXY_TARGET in .env.local if the API runs elsewhere */
const API_PROXY_TARGET = process.env.API_PROXY_TARGET || "http://127.0.0.1:8090";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: `${API_PROXY_TARGET.replace(/\/$/, "")}/api/:path*`,
      },
    ];
  },
};

export default nextConfig;
