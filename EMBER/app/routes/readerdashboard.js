import Route from '@ember/routing/route';

export default class ReaderdashboardRoute extends Route {
  setupController(controller, model) {
    controller.set('myVariable', model.myVariable);
  }

  model(params) {
    const myVariable = params.myVariable;
    return {
      myVariable,
    };
  }
}
