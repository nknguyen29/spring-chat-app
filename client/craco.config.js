const path = require('path');

module.exports = {
  webpack: {
    alias: {
      '@/components': path.resolve(__dirname, 'src/components'),
      '@/lib/utils': path.resolve(__dirname, 'src/lib/utils'),
    },
  },
};