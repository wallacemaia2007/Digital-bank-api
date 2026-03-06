/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        // Cores principais do design
        primary: {
          DEFAULT: "#3b82f6", // Azul principal
          light: "#60a5fa", // Azul claro  
          dark: "#2563eb", // Azul escuro
          lighter: "#93c5fd", // Azul mais claro
        },

        secondary: {
          DEFAULT: "#06b6d4", // Ciano/Verde água
          light: "#22d3ee", // Ciano claro
          dark: "#0891b2", // Ciano escuro
        },

        // Tema Escuro
        dark: {
          50: "#fafafa", // Muito claro (para texto em dark mode)
          100: "#f4f4f5", 
          200: "#e4e4e7",
          300: "#d4d4d8",
          400: "#a1a1aa",
          500: "#71717a",
          600: "#52525b",
          700: "#3f3f46",
          800: "#27272a", // Background secundário
          900: "#1a1a1a", // Background principal escuro
          950: "#0f0f0f", // Background mais escuro
        },

        // Tema Claro  
        light: {
          50: "#ffffff", // Branco puro
          100: "#fafafa", // Off-white
          200: "#f3f4f6", // Cinza muito claro
          300: "#e5e7eb", // Cinza claro (bordas)
          400: "#d1d5db", // Cinza médio-claro
          500: "#9ca3af", // Cinza médio
          600: "#6b7280", // Cinza médio-escuro
          700: "#374151", // Cinza escuro
          800: "#1f2937", // Quase preto
          900: "#111827", // Preto
        },

        // Cores de status
        success: {
          DEFAULT: "#10b981",
          light: "#34d399", 
          dark: "#059669",
        },

        warning: {
          DEFAULT: "#f59e0b",
          light: "#fbbf24",
          dark: "#d97706", 
        },

        error: {
          DEFAULT: "#ef4444",
          light: "#f87171",
          dark: "#dc2626",
        },

        // Cores originais mantidas para compatibilidade
        beige: {
          DEFAULT: "#E8DDD3", 
          light: "#F5F0EA", 
          dark: "#e6d9ceff", 
        },

        "custom-black": {
          DEFAULT: "#2D2D2D",
          light: "#404040", 
          lighter: "#545454", 
        },
      },

      boxShadow: {
        sm: "0 1px 2px 0 rgba(155, 27, 31, 0.05)",
        md: "0 4px 6px -1px rgba(155, 27, 31, 0.1)",
        lg: "0 10px 15px -3px rgba(155, 27, 31, 0.1)",
        xl: "0 20px 25px -5px rgba(155, 27, 31, 0.1)",
        "2xl": "0 25px 50px -12px rgba(155, 27, 31, 0.25)",
        glow: "0 0 20px rgba(155, 27, 31, 0.3)",
        "glow-sm": "0 0 10px rgba(155, 27, 31, 0.2)",
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
