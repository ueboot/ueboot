/**
 * 本地预览
 */

const path = require('path');
const webpack = require('webpack');
// const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const merge = require('webpack-merge');
const webpackBaseConfig = require('./webpack.base.config.js');
const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin')



module.exports = merge(webpackBaseConfig, {
    devtool: 'eval-source-map',

    // 入口
    entry: {
        app:['babel-polyfill','./examples/main'],
        // main: './examples/main',
        vendors: ['vue', 'vue-router']
    },
    // 输出
    output: {
        path: path.join(__dirname, '../examples/dist'),
        publicPath: '',
        filename: '[name].js',
        chunkFilename: '[name].chunk.js'
    },
    resolve: {
        alias: {
            ueboot: '../../src/index',
            iview: 'iview/dist/iview.js',
            vue: 'vue/dist/vue.esm.js',
            // vue: 'vue/dist/vue.runtime.js'
        }
    },
    devServer: {
        clientLogLevel: 'warning',
        historyApiFallback: {
            rewrites: [
                { from: /.*/, to: path.posix.join("/", 'index.html') },
            ],
        },
        host:'192.168.1.103',
        hot: true,
        contentBase: false, // since we use CopyWebpackPlugin.
        compress: true,
        open: false,
        overlay: false,
        publicPath: "/",
        proxy: {
            //本地代理
            '/ueboot/*': {
                target: 'http://localhost:8000',
                    debug:true,
                    changeOrigin: true,
                    secure: false
            }
        },
        quiet: true, // necessary for FriendlyErrorsPlugin
        watchOptions: {
            poll:false,
        }
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin({ name: 'vendors', filename: 'vendor.bundle.js' }),
        new HtmlWebpackPlugin({
            inject: true,
            filename: path.join(__dirname, '../examples/dist/index.html'),
            template: path.join(__dirname, '../examples/index.html')
        }),
        new FriendlyErrorsPlugin(),
        new CopyWebpackPlugin([
            {
                from: path.resolve(__dirname, '../static'),
                to: "static",
                ignore: ['.*']
            }
        ])
    ]
});
