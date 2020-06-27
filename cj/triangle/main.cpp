#include <GL/glut.h>
#include <stdio.h>
#include <vector>
using namespace std;
int n = 0;

struct RGBColor
{
    float red;
    float green;
    float blue;
};


struct Point
{
    float ax;
    float ay;
    RGBColor color;
};

vector<Point> myPoints;

RGBColor getRedColor(){
    RGBColor redColor;
    redColor.red = 1.0;
    redColor.blue = 0.0;
    redColor.green = 0.0;
    return redColor;
}

RGBColor getGreenColor(){
    RGBColor greenColor;
    greenColor.red = 0.0;
    greenColor.blue = 0.0;
    greenColor.green = 1.0;
    return greenColor;
}

RGBColor getBlueColor(){
    RGBColor blueColor;
    blueColor.red = 0.0;
    blueColor.blue = 1.0;
    blueColor.green = 0.0;
    return blueColor;
}

RGBColor divideColors(RGBColor color1, RGBColor color2){
    RGBColor newColor;
    newColor.red = ( color1.red + color2.red ) / 2.0;
    newColor.green = ( color1.green + color2.green ) / 2.0;
    newColor.blue = ( color1.blue + color2.blue ) / 2.0;
    return newColor;
}

void triangle_custom(Point point1, Point point2, Point point3)
{
    myPoints.push_back(point1);
    myPoints.push_back(point2);
    myPoints.push_back(point3);
}

void divide_triangle_custom(Point point1, Point point2, Point point3, int k)
{
    if (k > 0)
    {
        Point newPoint1, newPoint2, newPoint3;

        newPoint1.ax = (point1.ax + point2.ax) / 2.0;
        newPoint1.ay = (point1.ay + point2.ay) / 2.0;
        newPoint1.color = divideColors(point1.color, point2.color);

        newPoint2.ax = (point2.ax + point3.ax) / 2.0;
        newPoint2.ay = (point2.ay + point3.ay) / 2.0;
        newPoint2.color = divideColors(point2.color, point3.color);

        newPoint3.ax = (point1.ax + point3.ax) / 2.0;
        newPoint3.ay = (point1.ay + point3.ay) / 2.0;
        newPoint3.color = divideColors(point1.color, point3.color);

        divide_triangle_custom(point1, newPoint1, newPoint3, k - 1);
        divide_triangle_custom(newPoint1, point2, newPoint2, k - 1);
        divide_triangle_custom(newPoint3, newPoint2, point3, k - 1);
    }
    else
    {
        triangle_custom(point1, point2, point3);
    }
}

void display_custom(void)
{
    glClearColor(1.0, 1.0, 1.0, 1.0);
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(0.0, 0.0, 0.0);

    myPoints.clear();

    Point point1, point2, point3;

    point1.ax = 0.0;
    point1.ay = 0.0;
    point1.color = getRedColor();


    point2.ax = 10.0;
    point2.ay = 0.0;
    point2.color = getGreenColor();

    point3.ax = 5.0;
    point3.ay = 8.66666;
    point3.color = getBlueColor();

    divide_triangle_custom(point1, point2, point3, n);
    printf("n = %d\n", n);
    printf("size = %d\n", myPoints.size());
    glBegin(GL_TRIANGLES);
    for (int i = 0; i < myPoints.size(); i++){
        Point current = myPoints[i];
        RGBColor color = current.color;

        glColor3f(color.red, color.green, color.blue);
        glVertex2d(current.ax, current.ay);
    }
    glEnd();
    glFlush();
}

void reshape(int w, int h)
{
    glViewport(0, 0, (GLsizei)w, (GLsizei)h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(-1.0 * w / h, 11.0 * w / h, -1.0, 11.0);
    glMatrixMode(GL_MODELVIEW);
}

void mouse(int button, int state, int x, int y)
{
    switch (button)
    {
    case GLUT_LEFT_BUTTON:
        if (state == GLUT_DOWN)
        {
            n++;
            glutPostRedisplay();
        }
        break;
    case GLUT_RIGHT_BUTTON:
        if (state == GLUT_DOWN)
        {
            n--;
            glutPostRedisplay();
        }
        break;
    default:
        break;
    }
}

void init(void)
{
    glClearColor(1.0, 1.0, 1.0, 1.0);
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(0.0, 0.0, 0.0);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(-1.0, 11.0, -1.0, 11.0);
    glMatrixMode(GL_MODELVIEW);
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(600, 600);
    glutCreateWindow("Sierpinsk Triangle");
    glutDisplayFunc(display_custom);
    glutReshapeFunc(reshape);
    glutMouseFunc(mouse);
    init();
    glutMainLoop();
    return 0;
}