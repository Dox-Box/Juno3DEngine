#version 330 

in vec2 textureCoords;

out vec4 out_color;

uniform sampler2D guiTex;

void main(void){


	out_color = texture(guiTex,textureCoords);

}