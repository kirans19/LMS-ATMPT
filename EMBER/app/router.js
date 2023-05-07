import EmberRouter from '@ember/routing/router';
import config from 'lms-atmpt/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  this.route('signup');
  this.route('signin');

  this.route('readerdashboard', { path: '/readerdashboard/:myVariable' });

  this.route('publisherdashboard');
  this.route('staffdashboard');
});
