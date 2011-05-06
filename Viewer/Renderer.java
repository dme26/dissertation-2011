import java.io.File;


import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


public class Renderer implements GLEventListener{
	private GLU glu = new GLU();
	DunedinMap dunedinMap;
	Sun sun;
	Camera camera;
	public static float scale = 0.1f;
	public static boolean wireframe = false;
	public Renderer(File f){
		dunedinMap = new DunedinMap(f);
		sun = new Sun(0,1000,0);
		camera = new Camera();
	}
	public void update(){
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		update();
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();                                 // Reset The Matrix
		//camera.setPosition(-11f, 18.8f, 21f, 27f, 28f);
		camera.set(gl);
		gl.glScalef(scale, scale, scale);
		if(wireframe)
			gl.glPolygonMode( GL2.GL_FRONT_AND_BACK, GL2.GL_LINE );
		else
			gl.glPolygonMode( GL2.GL_FRONT_AND_BACK, GL2.GL_FILL );
		//sun.draw(gl);
		dunedinMap.draw(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		dunedinMap.deleteVertexBuffer(drawable.getGL().getGL2());
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glShadeModel(GL2.GL_SMOOTH);             // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);   // Black Background
		gl.glClearDepth(1.0f);                     // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);             // Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);              // The Type Of Depth Testing To Do
		
		gl.glEnable(GL2.GL_LIGHTING);              // Helpful lighting settings
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glColorMaterial( GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE ) ;
		gl.glEnable ( GL2.GL_COLOR_MATERIAL ) ;
		// Really Nice Perspective Calculations
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		dunedinMap.init(gl);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int xstart, int ystart, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();

        height = (height == 0) ? 1 : height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, (float) width / height, 1, 1000);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();		
	}

}
