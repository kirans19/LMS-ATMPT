import Controller from '@ember/controller';

import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';

export default class SigninController extends Controller {
  // @service session;

  @service router;
  @tracked email = '';
  @tracked password = '';

  @action
  async submitLoginForm(event) {
    event.preventDefault();
    const response = await fetch('http://localhost:8080/LMS_ATMPT/signin', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: this.email,
        password: this.password,
      }),
    });

    if (response.ok) {
      const data = await response.json();
      if (data.user_type == '1') {
        this.router.transitionTo('/readerdashboard/1');
      } else if (data.user_type == '2') {
        this.router.transitionTo('/publisherdashboard');
      } else if (data.user_type=='3') {
        this.router.transitionTo('/staffdashboard');
      }
    } else {
      console.error('Signup failed!');
    }
  }
}
