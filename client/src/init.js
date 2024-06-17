window.global ||= window;
// webpack to vite migration "hack"
// explanation: there is no global object in vite,
// some dependencies may use global object (webpack has global object)
// in an attempt to migrate from webpack to vite, 
// we need to create a global object