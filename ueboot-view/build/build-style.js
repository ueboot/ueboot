const gulp = require('gulp');
const cleanCSS = require('gulp-clean-css');
const less = require('gulp-less');
const rename = require('gulp-rename');
const autoprefixer = require('gulp-autoprefixer');

gulp.task('css', function () {
    // 编译less
    gulp.src('../src/styles/index.less')
        .pipe(less({ javascriptEnabled: true}))
        .pipe(autoprefixer({
            browsers: ['last 2 versions', 'ie > 8']
        }))
        .pipe(cleanCSS())
        .pipe(rename('ueboot.css'))
        .pipe(gulp.dest('../dist/styles'));
    
});
// copy图片文件
gulp.task('images', function () {
    gulp.src('../src/asserts/*.*')
        .pipe(gulp.dest('../dist/asserts'));
});
// 拷贝字体文件
gulp.task('fonts', function () {
    gulp.src('../src/styles/iview/common/iconfont/fonts/*.*')
        .pipe(gulp.dest('../dist/styles/fonts'));
});

gulp.task('default', ['css', 'fonts']);
// gulp.task('default',function () {
//
//
//
// });
