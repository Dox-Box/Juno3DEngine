#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[2];
in vec3 toCameraVector;

out vec4  out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor[2];
uniform float shineDamper;
uniform float reflectivity;

void main(void){

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	float nDot1;
	vec3 resultDiffuse = vec3(0,0,0);
	vec3 resultSpecular = vec3(0,0,0);

	for(int i=0;i<2;i++){
	vec3 unitLightVector = normalize(toLightVector[i]);
	nDot1 =  dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.0);
	vec3 resultDiffuse = resultDiffuse + brightness * lightColor[i];
	
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	
	float specularFactor = dot(reflectedLightDirection, unitVectorToCamera); 
	specularFactor = max(specularFactor, 0.0);
	float dampenedFactor = pow(specularFactor, shineDamper);
	resultSpecular = resultSpecular + dampenedFactor *reflectivity * lightColor[i];
	}
	float brightness = max(nDot1,0.28);
	vec4 textureColor = texture(textureSampler, pass_textureCoords);
	if(textureColor.a<0.5){
		discard;
	}
	resultDiffuse = max(resultDiffuse, 0.25);
	out_color = vec4(resultDiffuse,1.0) * textureColor*brightness + vec4(resultSpecular, 1.0);	
	
	
	}