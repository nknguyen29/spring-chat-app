// Vite Configuration
// By default, Vite will look for a vite.config.js file in your project root.
// If it does not exist, Vite will use its built-in configuration.
// @see: https://vitejs.dev/config/

import { defineConfig } from "vite";

// The build configuration tells Vite where to output the compiled files.
// In this case, the compiled files are output to the src/main/resources/static directory.
// This is the directory where Spring Boot serves static files from.

export default defineConfig({
    build: {
        outDir: "src/main/resources/static",
        assetsDir: ".",
        rollupOptions: {
            input: "src/main/resources/assets/main.js",
        },
        emptyOutDir: true, // clean the output directory before building
    },
});
