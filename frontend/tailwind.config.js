/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#3b82f6", // Azul principal (botões, badge ativo)
          light: "#60a5fa", // Azul claro (hover, badges positivos)
          lighter: "#c3d9f5", // Azul claro (hover, badges positivos)
          dark: "#2563eb", // Azul escuro (hover dark, pressed)
        },

        "t-light": {
          DEFAULT: "#f0f4ff", // Background principal light (raiz azulada)
          light: "#ffffff", // Cards / sidebar / superfícies elevadas
          dark: "#e2e8f4", // Bordas, inputs, separadores
        },

        "t-dark": {
          DEFAULT: "#07101f", // Background principal dark (main area)
          light: "#060e20", // Sidebar / container primário
          lighter: "#111827", // Cards, superfícies elevadas
        },

        accent: {
          DEFAULT: "#34d399", // Verde positivo (+$27.00, % up)
          light: "#6ee7b7", // Verde claro
          dark: "#10b981", // Verde escuro
        },
      },

      boxShadow: {
        sm: "0 1px 3px rgba(0,0,0,0.12)",
        md: "0 4px 12px rgba(0,0,0,0.15)",
        lg: "0 10px 30px rgba(0,0,0,0.20)",
        xl: "0 20px 40px rgba(0,0,0,0.25)",
        "2xl": "0 25px 50px rgba(0,0,0,0.30)",
        glow: "0 0 20px rgba(59,130,246,0.30)",
        "glow-sm": "0 0 10px rgba(59,130,246,0.20)",
      },

      animation: {
        "fade-in": "fadeIn 0.5s ease-in",
        "slide-up": "slideUp 0.5s ease-out",
        "slide-down": "slideDown 0.5s ease-out",
        "spin-slow": "spin 3s linear infinite",
      },

      keyframes: {
        fadeIn: {
          "0%": { opacity: "0" },
          "100%": { opacity: "1" },
        },
        slideUp: {
          "0%": { transform: "translateY(20px)", opacity: "0" },
          "100%": { transform: "translateY(0)", opacity: "1" },
        },
        slideDown: {
          "0%": { transform: "translateY(-20px)", opacity: "0" },
          "100%": { transform: "translateY(0)", opacity: "1" },
        },
      },
    },
  },
  plugins: [],
};
