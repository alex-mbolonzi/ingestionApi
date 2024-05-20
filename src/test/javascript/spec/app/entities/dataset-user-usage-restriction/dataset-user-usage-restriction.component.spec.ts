import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetUserUsageRestrictionComponent } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction.component';
import { DatasetUserUsageRestrictionService } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction.service';
import { DatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

describe('Component Tests', () => {
  describe('DatasetUserUsageRestriction Management Component', () => {
    let comp: DatasetUserUsageRestrictionComponent;
    let fixture: ComponentFixture<DatasetUserUsageRestrictionComponent>;
    let service: DatasetUserUsageRestrictionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetUserUsageRestrictionComponent],
        providers: [],
      })
        .overrideTemplate(DatasetUserUsageRestrictionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetUserUsageRestrictionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetUserUsageRestrictionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetUserUsageRestriction(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetUserUsageRestrictions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
