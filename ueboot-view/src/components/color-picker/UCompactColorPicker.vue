<template>
    <div>
        <Row v-if="fixed">
            <compact-picker v-model="colors" @input="changeColor" :palette="palette"/>
        </Row>
        <Row v-else>
            <Dropdown trigger="custom" style="width:100%;" :visible="visible"
                      @on-clickoutside="handleClose">
                <i-input v-model="inputColor" disabled>
                    <div slot="prepend" class="selectedColor" :style="{background:inputColor}"></div>
                    <span slot="append">
                         <Icon type="md-color-fill" slot="suffix" @click="showColorModal"/>
                    </span>
                </i-input>
                <div slot="list" class="colorParent">
                    <Row type="flex" align="middle">
                        <i-col span="10">
                            <div :style="{background:inputColor}" class="colorModel"></div>
                        </i-col>
                        <i-col span="10" offset="4">
                            <i-input class="ipt" v-model="inputColor" :maxlength="7" @keyup.enter="iptColor(inputColor)"></i-input>

                        </i-col>
                    </Row>
                    <div class="compact-picker-parent">
                        <compact-picker v-model="colors" @input="changeColor" :palette="palette"/>
                    </div>
                    <div style="text-align:right;margin:10px;">
                        <Button type="info" size="small" @click="handleClose">关闭</Button>
                    </div>
                </div>
            </Dropdown>
        </Row>
    </div>
</template>
<script>
    import {Compact} from 'vue-color';

    const defaultColors = [
        '#4D4D4D', '#999999', '#FFFFFF', '#F44E3B', '#FE9200', '#FCDC00',
        '#DBDF00', '#A4DD00', '#68CCCA', '#73D8FF', '#AEA1FF', '#FDA1FF',
        '#333333', '#808080', '#CCCCCC', '#D33115', '#E27300', '#FCC400',
        '#B0BC00', '#68BC00', '#16A5A5', '#009CE0', '#7B64FF', '#FA28FF',
        '#000000', '#666666', '#B3B3B3', '#9F0500', '#C45100', '#FB9E00',
        '#808900', '#194D33', '#0C797D', '#0062B1', '#653294', '#AB149E'
    ]
    export default {
        name: "UColorPicker",
        components: {
            'compact-picker': Compact
        },
        // 从父向子
        props: {
            // 是否固定显示，固定则默认带输入框并全部展示
            fixed: {
                type: Boolean,
                default: false
            },
            palette: {
                type: Array,
                default () {
                    return defaultColors
                }
            },
            // 组件使用v-model即可获取返回值
            value: {
                type: [String, Number],
                default: ''
            },
        },
        data() {
            let colors = '#AB149F'
            if(this.value){
                colors = this.value===''?'#AB149F':this.value
            }
            return {
                visible: false,
                //如果没有初始值，则设置为一个不存在defaultColor列表当中的值，避免出现默认白点选中的情况
                colors: colors,
                inputColor: this.value,
            }
        },
        created() {
        },
        methods: {
            handleClose() {
                this.visible = false
            },
            showColorModal() {
                this.visible = true
            },
            changeColor() {
                this.inputColor = this.colors.hex;
                if (!this.fixed) {
                    this.visible = false
                }
                this.$emit('input', this.colors.hex)
            },
            iptColor(value) {
                if (value[0] !== '#') {
                    value = "#" + value
                }
                let reg = /^#[\da-f]{3}([\da-f]{3})?$/i;
                if (!reg.test(value)) {
                    this.$Message.warning('输入颜色有误，请重新输入')
                    return;
                }
                value = value.toUpperCase();
                if (value.length === 4) {
                    value = value[0] + value[1] + value[1] + value[2] + value[2] + value[3] + value[3]
                }
                for (let i = 0; i < this.palette.length; i++) {
                    if (value === this.palette[i]) {
                        this.colors = value;
                        this.inputColor = value;
                        return;
                    }
                }
                this.$Message.warning('输入颜色未在选区范围内，请重新选择')
            }
        },
        watch: {
            value: function (newValue, oldValue) {
                this.inputColor = newValue
                //如果没有初始值，则设置为一个不存在defaultColor列表当中的值，避免出现默认白点选中的情况
                //重置表单时会触发该代码
                let colors = '#AB149F'
                if(newValue){
                    colors = newValue===''?'#AB149F':newValue
                }
                this.colors = colors
            },
            inputColor: function (val) {
                this.$emit("onnColorChange", val)
            }
        }
    }
</script>

<style scoped>
    .vc-compact {
        width: 100%;
        padding-top: 5px;
        padding-left: 5px;
        border-radius: 2px;
        background-color: #fff;
        box-shadow: none;
    }
    .vc-compact .vc-compact-color-item {
        width: 20px;
        height: 20px;
    }

    .colorParent {
        margin: 10px;
    }
    .colorModel {
        width: 100%;
        height: 30px;
        border-radius: 4px;
        box-shadow: 0 0 2px #999999;
    }

    .compact-picker-parent {
        margin-top: 20px;
    }
    .selectedColor {
        min-height: 22px;
        min-width: 22px;
        height: 100%;
        width: 100%;
    }

</style>
