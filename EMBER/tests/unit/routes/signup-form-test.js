import { module, test } from 'qunit';
import { setupTest } from 'lms-atmpt/tests/helpers';

module('Unit | Route | signup-form', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:signup-form');
    assert.ok(route);
  });
});
