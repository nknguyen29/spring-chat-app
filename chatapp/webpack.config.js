/**
 * Webpack configuration file.
 *
 * Webpack is a module bundler that takes assets such as JavaScript, CSS, and images
 * and bundles them into a single file that can be served to the browser.
 * @see: https://webpack.js.org/
 *
 * This file is used to configure Webpack for the project.
 * By default, Webpack will look for a webpack.config.js file in your project root.
 * If it does not exist, Webpack will use its built-in configuration.
 */

const path = require('path');
const TerserPlugin = require('terser-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WarningsToErrorsPlugin = require('warnings-to-errors-webpack-plugin');


/**
 * Webpack configuration.
 * @param {Object} env - Environment variables.
 * @param {Object} argv - Command line arguments.
 * @returns {Object} Webpack configuration.
 * @see: https://webpack.js.org/configuration/configuration-types/#exporting-a-function
 */
module.exports = (env, argv) => ({
  entry: './src/main/resources/js/app.js',
  output: {
    path: path.resolve(__dirname, './target/classes/static'),
    filename: 'js/bundle.js'
  },
  devtool: argv.mode === 'production' ? false : 'eval-source-map',
  optimization: {
    minimize: true,
    minimizer: [
      new TerserPlugin(),
      new CssMinimizerPlugin()
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: "css/bundle.css"
    }),
    new WarningsToErrorsPlugin()
  ],
  module: {
    rules: [
      {
        test: /\.js$'/,
        include: path.resolve(__dirname, './src/main/resources/js'),
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
          },
        },
      },
      {
        test: /\.css$/,
        include: path.resolve(__dirname, './src/main/resources/css'),
        use: [
          argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader',
          {
            loader: 'css-loader',
            options: {
              importLoaders: 1
            }
          },
          {
            loader: 'postcss-loader',
            options: {
              postcssOptions: {
                plugins: [
                  require('tailwindcss'),
                  require('autoprefixer'),
                ]
              }
            }
          }
        ]
      }
    ]
  },
  resolve: {
    modules: [
      path.resolve(__dirname, './src/main/resources'),
      'node_modules'
    ],
  },
  devServer: {
    port: 9000,
    compress: true,
    watchFiles: [
      'src/main/resources/templates/**/*.html',
      'src/main/resources/js/**/*.js',
      'src/main/resources/css/**/*.css'
    ],
    proxy: [
      {
        context: '**',
        target: 'http://localhost:8080',
        secure: false,
        prependPath: false,
        headers: {
          'X-Devserver': '1',
        }
      }
    ]
  }
});
