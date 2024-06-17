const defaultTheme = require('tailwindcss/defaultTheme');


/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/**/*.{html,js}",
    "./node_modules/flowbite/**/*.js",
  ],
  safelist: ["underline", "bg-gray-100"],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', ...defaultTheme.fontFamily.sans],
        mono: ['JetBrains Mono', ...defaultTheme.fontFamily.mono]
      }
    },
    container: {
      center: true,
    },
  },
  plugins: [
    // Uncomment the following lines to enable additional Tailwind CSS plugins
    // require('@tailwindcss/typography'), // Provides beautiful typographic defaults
    // require('@tailwindcss/forms'), // Provides better default styles for forms
    // require('@tailwindcss/aspect-ratio'), // Provides utilities for aspect ratios
    // require('@tailwindcss/container-queries'), // Provides support for CSS container queries
    // Add other plugins as needed
    require('flowbite/plugin'),
  ]
};
