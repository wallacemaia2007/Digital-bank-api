/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#294153',
        'custom-gray': '#F2F2F2',
        'custom-dark-gray': '#E8E8E8'
      }
    },
  },
  plugins: [],
}