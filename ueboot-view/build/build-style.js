var gulp = require('gulp');
var cleanCSS = require('gulp-clean-css');
var less = require('gulp-less');
var rename = require('gulp-rename');
var autoprefixer = require('gulp-autoprefixer');

// 编译less
gulp.task('css', function () {
  gulp.src('../src/components/tree/css/style.css')
    .pipe(autoprefixer({
      browsers: ['last 2 versions', 'ie > 8']
    }))
    .pipe(cleanCSS())
    .pipe(rename('ueboot.css'))
    .pipe(gulp.dest('../ueboot/styles'));
});

// copy图片文件
gulp.task('images', function () {
  gulp.src(['../src/components/tree/css/*.png','../src/components/tree/css/*.gif'])
    .pipe(gulp.dest('../ueboot/styles'));
});

// 拷贝字体文件
gulp.task('fonts', function () {
  gulp.src('../src/styles/common/iconfont/fonts/*.*')
    .pipe(gulp.dest('../dist/styles/fonts'));
});

gulp.task('default', ['css','images', 'fonts']);
