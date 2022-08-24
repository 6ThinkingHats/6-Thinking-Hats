<template>
<div></div>
</template>

<script>
export default {
	name: 'BackgroundWave',
	components: {
	},
	data: () => {
		return {
		}
	},
	computed: {
	},
	methods: {
	},
}
class background{
    constructor() {
      this.canvas = document.createElement('canvas');
  
      
      this.ctx = this.canvas.getContext('2d');
  
      document.body.appendChild(this.canvas);
  
   
      window.addEventListener('resize', this.resize.bind(this), {
        once: false,
        passive: false,
        capture: false,
      });
  
      this.waveGroup = new WaveGroup();
  
      this.resize();
  
     
      requestAnimationFrame(this.animate.bind(this));
    }
  
    resize() {
      this.stageWidth = document.body.clientWidth;
      this.stageHeight = document.body.clientHeight;
  
      this.canvas.width = this.stageWidth * 2;
      this.canvas.height = this.stageHeight * 2;
  
     
      this.ctx.scale(2, 2);
  
      this.waveGroup.resize(this.stageWidth, this.stageHeight);
    }
  
    animate() {
      this.ctx.clearRect(0, 0, this.stageWidth, this.stageHeight);
      this.waveGroup.draw(this.ctx);
      requestAnimationFrame(this.animate.bind(this));
    }
  }
  class Wave {
    constructor(color) {
      this.color = color;
      this.points = [];
      this.numberOfPoints = 6;
    }
  
    resize(stageWidth, stageHeight) {
      this.stageWidth = stageWidth;
      this.stageHeight = stageHeight;
  
      this.centerX = stageWidth / 2;
      this.centerY = stageHeight* 11/12;
  
     
      this.pointGap = this.stageWidth / (this.numberOfPoints - 1);
  
      this.init();
    }
  
    init() {
      for (let i = 0; i < this.numberOfPoints; i++) {
        this.points[i] = new Point(i, this.pointGap * i, this.centerY);
      }
    }
  
    draw(ctx) {
        ctx.beginPath();
    
        let prevX = this.points[0].x;
        let prevY = this.points[0].y;
    
        ctx.moveTo(prevX, prevY);
    
        for (let i = 0; i < this.numberOfPoints; i++) {
          const cx = (prevX + this.points[i].x) / 2;
          const cy = (prevY + this.points[i].y) / 2;
    
          ctx.quadraticCurveTo(prevX, prevY, cx, cy);
    
          prevX = this.points[i].x;
          prevY = this.points[i].y;
    
          if (i != 0 && i != this.numberOfPoints - 1) {
            this.points[i].update();
          }
        }
    
        ctx.lineTo(prevX, prevY);
        ctx.lineTo(this.stageWidth, this.stageHeight);
        ctx.lineTo(0, this.stageHeight);
        ctx.lineTo(this.points[0].x, this.points[0].y);
    
        ctx.fillStyle = this.color;
        ctx.fill();
    
        ctx.closePath();
      }
    }

class Point {
    
    constructor(index, x, y) {
      this.x = x;
      this.y = y;
      this.fieldY = y;
      this.speed = 0.05;
      this.cur = index; 
      this.max = Math.random() * 170+30;
    }
  
    update() {
      
      this.cur += this.speed;
    
      this.y = this.fieldY + Math.sin(this.cur) * this.max;
    }
}
class WaveGroup{
    constructor() {
        this.totalWaves = 3;
        this.totalPoints = 6;
        this.color = ['rgba(255,0,0,0.4)', 'rgba(255,255,0,0.4)', 'rgba(0,255,255,0.4)'];
        this.waves = [];
        for (let i = 0; i < this.totalWaves; i++){
            const wave = new Wave(
                this.color[i],
            );
            this.waves[i] = wave;
        }
    }
    resize(stageWidth, stageHeight) {
        for (let i = 0; i < this.totalWaves; i++){
            const wave = this.waves[i];
            wave.resize(stageWidth, stageHeight);
        }
    }
    draw(ctx) {
        for (let i = 0; i < this.totalWaves; i++){
            const wave = this.waves[i];
            wave.draw(ctx);
        }
    }
  }
  window.onload = () => {
    new background();
  };
</script>

<style>

  canvas {
    width: 100%;
  }
</style>