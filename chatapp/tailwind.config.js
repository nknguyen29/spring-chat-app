/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/**/*.{html,js}",
    "./node_modules/flowbite/**/*.js",
  ],
  safelist: ["underline", "bg-gray-100"],
  theme: {
    extend: {},
    container: {
      center: true,
    },
  },
  plugins: [
    // require('@tailwindcss/typography'),
    // require('@tailwindcss/forms'),
    // require('@tailwindcss/aspect-ratio'),
    // require('@tailwindcss/container-queries'),
    // ...
    require('flowbite/plugin'),
  ],
};
